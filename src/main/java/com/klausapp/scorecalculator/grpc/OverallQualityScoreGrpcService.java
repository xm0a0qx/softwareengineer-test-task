package com.klausapp.scorecalculator.grpc;


import com.klausapp.scorecalculator.service.OverallScoreService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class OverallQualityScoreGrpcService extends OverallQualityScoreServiceGrpc.OverallQualityScoreServiceImplBase {
    private final OverallScoreService overallScoreService;

    public OverallQualityScoreGrpcService(OverallScoreService overallScoreService) {
        this.overallScoreService = overallScoreService;
    }

    @Override
    public void getScore(OverallQualityScoreRequest request, StreamObserver<OverallQualityScoreResponse> responseObserver) {
        float overallScore = overallScoreService.getOverallScore(request.getPeriodStart(), request.getPeriodEnd());
        responseObserver.onNext(OverallQualityScoreResponse.newBuilder().setScorePercentage(overallScore).build());
        responseObserver.onCompleted();
    }
}
