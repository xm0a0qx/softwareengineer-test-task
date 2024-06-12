package com.klausapp.scorecalculator.grpc;


import com.klausapp.scorecalculator.service.ScoreChangeService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class ScoreChangeGrpcService extends ScoreChangeServiceGrpc.ScoreChangeServiceImplBase {
    private final ScoreChangeService scoreChangeService;

    public ScoreChangeGrpcService(ScoreChangeService scoreChangeService) {
        this.scoreChangeService = scoreChangeService;
    }

    @Override
    public void getScore(ScoreChangeRequest request, StreamObserver<ScoreChangeResponse> responseObserver) {
        float scoreChangeOverPeriod = scoreChangeService.getScoreChangeOverPeriod(request.getFirstPeriodStart(), request.getFirstPeriodEnd(), request.getSecondPeriodStart(), request.getSecondPeriodEnd());
        responseObserver.onNext(ScoreChangeResponse.newBuilder().setScorePercentage(scoreChangeOverPeriod).build());
        responseObserver.onCompleted();
    }
}
