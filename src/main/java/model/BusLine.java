package model;

import java.util.List;

public class BusLine {
    private int id;
    private String lineName;
    private int companyId;
    private String companyName; // 添加 companyName 字段
    private List<BusStop> stops; // 添加 stops 字段

    // 构造函数
    public BusLine() {
    }

    public BusLine(int id, String lineName, int companyId) {
        this.id = id;
        this.lineName = lineName;
        this.companyId = companyId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<BusStop> getStops() {
        return stops;
    }

    public void setStops(List<BusStop> stops) {
        this.stops = stops;
    }
}
