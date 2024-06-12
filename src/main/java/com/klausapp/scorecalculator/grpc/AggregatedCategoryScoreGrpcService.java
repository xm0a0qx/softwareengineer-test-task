package com.klausapp.scorecalculator.grpc;

import com.klausapp.scorecalculator.dto.AggregatedScoreResponseDto;
import com.klausapp.scorecalculator.service.AggregatedCategoryScoreService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class AggregatedCategoryScoreGrpcService extends AggregatedCategoryScoresServiceGrpc.AggregatedCategoryScoresServiceImplBase {

    private final AggregatedCategoryScoreService aggregatedCategoryScoreService;

    public AggregatedCategoryScoreGrpcService(AggregatedCategoryScoreService aggregatedCategoryScoreService) {
        this.aggregatedCategoryScoreService = aggregatedCategoryScoreService;
    }

    @Override
    public void getScores(AggregatedCategoryScoresRequest request, StreamObserver<AggregatedCategoryScoresResponse> responseObserver) {
        List<AggregatedCategoryScoresResponse.AggregatedCategoryScoresOverPeriod> response = getResponse(request);
        responseObserver.onNext(AggregatedCategoryScoresResponse.newBuilder().addAllCategoryScoreList(response).build());
        responseObserver.onCompleted();
    }

    private List<AggregatedCategoryScoresResponse.AggregatedCategoryScoresOverPeriod> getResponse(AggregatedCategoryScoresRequest request) {
        List<AggregatedScoreResponseDto> aggregatedScore = aggregatedCategoryScoreService.getAggregatedScoreOverPeriod(request.getPeriodStart(), request.getPeriodEnd());
        return createResponse(aggregatedScore);
    }

    private static List<AggregatedCategoryScoresResponse.AggregatedCategoryScoresOverPeriod> createResponse(List<AggregatedScoreResponseDto> scoresOverPeriod) {
        return scoresOverPeriod.stream()
                .map(categoryScoreByPeriodDTO -> AggregatedCategoryScoresResponse.AggregatedCategoryScoresOverPeriod.newBuilder()
                        .setCategoryName(categoryScoreByPeriodDTO.getCategoryName())
                        .setScore(categoryScoreByPeriodDTO.getScore())
                        .setDate(categoryScoreByPeriodDTO.getDate())
                        .build())
                .collect(Collectors.toList());
    }
}
