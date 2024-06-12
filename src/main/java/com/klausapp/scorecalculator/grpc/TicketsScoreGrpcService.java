package com.klausapp.scorecalculator.grpc;

import com.klausapp.scorecalculator.dto.TicketScoreResponseDto;
import com.klausapp.scorecalculator.service.TicketsScoreService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@GrpcService
public class TicketsScoreGrpcService extends TicketsScoreServiceGrpc.TicketsScoreServiceImplBase {

    private final TicketsScoreService ticketsScoreService;

    public TicketsScoreGrpcService(TicketsScoreService ticketsScoreService) {
        this.ticketsScoreService = ticketsScoreService;
    }

    @Override
    public void getScores(TicketScoreRequest request, StreamObserver<TicketScoreResponse> responseObserver) {
        Map<Integer, TicketScoreResponse.CategoryScoreList> categoryScoreMap = getCategoryScoreMap(request);
        responseObserver.onNext(TicketScoreResponse.newBuilder().putAllCategoryScoresByTickets(categoryScoreMap).build());
        responseObserver.onCompleted();
    }

    private Map<Integer, TicketScoreResponse.CategoryScoreList> getCategoryScoreMap(TicketScoreRequest request) {
        Map<Integer, List<TicketScoreResponseDto>> ticketScoreMap = ticketsScoreService.getTicketScore(request.getPeriodStart(), request.getPeriodEnd());
        return ticketScoreMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> createCategoryList(entry.getValue())));
    }

    private TicketScoreResponse.CategoryScoreList createCategoryList(List<TicketScoreResponseDto> ticketScoreResponseDtos) {
        List<TicketScoreResponse.CategoryScoreList.CategoryScore> resultList = ticketScoreResponseDtos.stream()
                .map(dto ->
                        TicketScoreResponse.CategoryScoreList.CategoryScore.newBuilder()
                                .setCategoryName(dto.getCategoryName())
                                .setScore(dto.getScore())
                                .build())
                .toList();

        return TicketScoreResponse.CategoryScoreList.newBuilder()
                .addAllCategoryScore(resultList)
                .build();
    }
}
