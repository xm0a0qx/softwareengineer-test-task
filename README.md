**Project requirements:**

* Java 17
* Maven

**How to run**

docker way
```bash
docker build -t sample .

docker run -p 8080:8080 -p 9090:9090 sample
```
maven way
```bash
mvn spring-boot:run
```

Docker container can be then deployed to k8s or minicube (in case if it local testing)

**Testing**

Application cab be tested using Postman or grpcurl to create gRPC calls to server.


localhost:9090 AggregatedCategoryScoresService.getScores
```json
{
    "periodEnd": "2019-07-30T17:43:14",
    "periodStart": "2019-07-25T17:43:14"
}
```
localhost:9090 ScoreChangeService.getScore
```json
{
    "firstPeriodEnd": "2019-07-26T17:43:14",
    "firstPeriodStart": "2019-07-25T17:43:14",
    "secondPeriodEnd": "2019-07-29T17:43:14",
    "secondPeriodStart": "2019-07-27T17:43:14"
}
```
localhost:9090 OverallQualityScoreService.getScore
```json
{
    "periodEnd": "2019-07-30T17:43:14",
    "periodStart": "2019-07-25T17:43:14"
}
```
localhost:9090 TicketsScoreService.getScore
```json
{
    "periodEnd": "2019-07-30T17:43:14",
    "periodStart": "2019-07-25T17:43:14"
}
```





# Software Engineer Test Task

As a test task for [Klaus](https://www.klausapp.com) software engineering position we ask our candidates to build a small [gRPC](https://grpc.io) service using language of their choice. Preferred language for new services in Klaus is [Go](https://golang.org).

The service should be using provided sample data from SQLite database (`database.db`).

Please fork this repository and share the link to your solution with us.

### Tasks

1. Come up with ticket score algorithm that accounts for rating category weights (available in `rating_categories` table). Ratings are given in a scale of 0 to 5. Score should be representable in percentages from 0 to 100. 

2. Build a service that can be queried using [gRPC](https://grpc.io/docs/tutorials/basic/go/) calls and can answer following questions:

    * **Aggregated category scores over a period of time**
    
        E.g. what have the daily ticket scores been for a past week or what were the scores between 1st and 31st of January.

        For periods longer than one month weekly aggregates should be returned instead of daily values.

        From the response the following UI representation should be possible:

        | Category | Ratings | Date 1 | Date 2 | ... | Score |
        |----|----|----|----|----|----|
        | Tone | 1 | 30% | N/A | N/A | X% |
        | Grammar | 2 | N/A | 90% | 100% | X% |
        | Random | 6 | 12% | 10% | 10% | X% |

    * **Scores by ticket**

        Aggregate scores for categories within defined period by ticket.

        E.g. what aggregate category scores tickets have within defined rating time range have.

        | Ticket ID | Category 1 | Category 2 |
        |----|----|----|
        | 1   |  100%  |  30%  |
        | 2   |  30%  |  80%  |

    * **Overall quality score**

        What is the overall aggregate score for a period.

        E.g. the overall score over past week has been 96%.

    * **Period over Period score change**

        What has been the change from selected period over previous period.

        E.g. current week vs. previous week or December vs. January change in percentages.


### Bonus

* How would you build and deploy the solution?

    At Klaus we make heavy use of containers and [Kubernetes](https://kubernetes.io).
