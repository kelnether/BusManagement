package test;

import service.BusStopService;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class BusStopServiceTest {
    public static void main(String[] args) throws SQLException {
        BusStopService busStopService = new BusStopService();
        List<Integer> stopIds = Arrays.asList(1, 2, 3); // 测试数据
        double totalDistance = busStopService.calculateTotalDistance(stopIds);
        System.out.println("Total Distance: " + totalDistance); // 应输出正确的总距离
    }
}
