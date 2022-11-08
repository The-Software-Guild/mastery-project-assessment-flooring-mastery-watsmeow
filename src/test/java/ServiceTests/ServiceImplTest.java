package ServiceTests;

import com.watsmeow.dao.DaoAuditInterface;
import com.watsmeow.dao.DaoImpl;
import com.watsmeow.dao.DaoInterface;
import com.watsmeow.dto.Order;
import com.watsmeow.dto.Product;
import com.watsmeow.dto.TaxInfo;
import com.watsmeow.service.ServiceImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Mockito.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ServiceImplTest {


    public static ServiceImpl testService;
    @Mock public static DaoImpl testDao;

    public static DaoAuditInterface testAuditDaoInterface;

    public ServiceImplTest() {}

    @org.junit.jupiter.api.BeforeAll
    public static void SetUpClass() throws Exception {
        testDao = Mockito.mock(DaoImpl.class);
        when(testDao.getProduct(any())).thenReturn(new Product("Wood", new BigDecimal(5.15),
                new BigDecimal(4.75)));
        when(testDao.getTaxInfo(any())).thenReturn(new TaxInfo("TX", "Texas",
                new BigDecimal(4.45)));
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
        testDao = Mockito.mock(DaoImpl.class);
        when(testDao.getProduct(any())).thenReturn(new Product("Wood", new BigDecimal(5.15).setScale(2, RoundingMode.HALF_UP),
                new BigDecimal(4.75).setScale(2, RoundingMode.HALF_UP)));
        when(testDao.getTaxInfo(any())).thenReturn(new TaxInfo("TX", "Texas",
                new BigDecimal(4.45).setScale(2, RoundingMode.HALF_UP)));
        when(testDao.getOrderNumbers()).thenReturn(new HashSet<>(Arrays.asList(1, 2, 3, 4, 5)));
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }

    @org.junit.jupiter.api.Test
    public void testGenerateFullOrder() throws Exception {
        testService = new ServiceImpl(testDao, testAuditDaoInterface);
        Order testOrder = new Order("Service TestName", "Texas", "Wood",
                new BigDecimal(155).setScale(2, RoundingMode.HALF_UP));
        Order newTestOrder = testService.generateFullOrder(testOrder);
        Assertions.assertTrue(newTestOrder.getTaxRate().equals(new BigDecimal(4.45).setScale(2, RoundingMode.HALF_UP)));
        Assertions.assertTrue(newTestOrder.getStateAbbrev().equals("TX"));
        Assertions.assertTrue(newTestOrder.getMaterialCost().equals(new BigDecimal(798.25).setScale(2, RoundingMode.HALF_UP)));
    }

    @org.junit.jupiter.api.Test
    public void testSaveOrder() throws Exception {
        testService = new ServiceImpl(testDao, testAuditDaoInterface);
        Order testOrder = new Order("Service TestName", "Texas", "Wood",
                new BigDecimal(155).setScale(2, RoundingMode.HALF_UP));
        testService.saveOrder(testOrder);
        Optional<Order> testOrderNumber = testDao.getAllOrders()
                .stream()
                .filter(order -> order.getOrderNumber() == 6)
                .findFirst();
        Assertions.assertTrue(testOrderNumber.isPresent());
    }
}
