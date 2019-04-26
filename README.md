# Plan Generator Assignment - Lendico 

This project implements a REST API that generates a repayment plan for a given loan detail.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

```
mvn package
```

## Running

Run with the following command

```
java -jar target\plangenerator-0.0.1-SNAPSHOT.jar
```

## Usage

This REST API can be consumed by sending POST requests to http://localhost:8080/generate-plan

Sample request body should be like this:

```
{ 
    "loanAmount": "5000", 
    "nominalRate": "5.0", 
    "duration": 24, 
    "startDate": "2018-01-01T00:00:01Z" 
}
```

For this request, response body will be produced as follows:

```
{ 
  [ 
    { 
        "borrowerPaymentAmount": "219.36", 
        "date": "2018-01-01T00:00:00Z", 
        "initialOutstandingPrincipal": "5000.00", 
        "interest": "20.83", 
        "principal": "198.53", 
        "remainingOutstandingPrincipal": "4801.47" 
    }, 
    { 
        "borrowerPaymentAmount": "219.36", 
        "date": "2018-02-01T00:00:00Z", 
        "initialOutstandingPrincipal": "4801.47", 
        "interest": "20.01", 
        "principal": "199.35", 
        "remainingOutstandingPrincipal": "4602.12" 
    }, 
    ... 
    { 
        "borrowerPaymentAmount": "219.28", 
        "date": "2020-01-01T00:00:00Z", 
        "initialOutstandingPrincipal": "218.37", 
        "interest": "0.91", 
        "principal": "218.37", 
        "remainingOutstandingPrincipal": "0"
    } 
  ] 
}
```

## Authors

* **Serkan Tuna** - [serkantuna](https://github.com/serkantuna)


