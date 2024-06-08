package com.teamtwo.trafficsystem.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TrafficData {
    private String stdHour;
    private String routeNo;
    private String routeName;
    private String updownTypeCode;
    private String vdsId;
    private String trafficAmout;
    private String shareRatio;
    private String conzoneId;
    private String conzoneName;
    private String stdDate;
    private String speed;
    private String timeAvg;
    private String grade;

}
