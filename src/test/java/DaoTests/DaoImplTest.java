package DaoTests;

import com.watsmeow.dao.DaoImpl;
import com.watsmeow.dto.Order;
import com.watsmeow.dto.Product;
import com.watsmeow.dto.TaxInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class DaoImplTest {

    public static DaoImpl testDao;

    public DaoImplTest() {}

    @org.junit.jupiter.api.BeforeAll
    public static void SetUpClass() throws Exception {
        testDao = new DaoImpl();
//        Order testOrder = new Order("Test Customer", "Washington", "Wood",
//                new BigDecimal(150));
//        testOrder.setOrderDate(LocalDate.now().plusDays(30000));
//        testOrder.setOrderNumber(14);
//        testOrder.setCustomerName("Test");
//        testOrder.setStateAbbrev("WA");
//        testOrder.setTaxRate(new BigDecimal(4.45));
//        testOrder.setProductType("Wood");
//        testOrder.setArea(new BigDecimal(300));
//        testOrder.setCostPerSqFt(new BigDecimal(5.15));
//        testOrder.setLaborCostPerSqFt(new BigDecimal(4.75));
//        testOrder.setMaterialCost(new BigDecimal(1287.50));
//        testOrder.setLaborCost(new BigDecimal(1187.50));
//        testOrder.setTax(new BigDecimal(110.14));
//        testOrder.setTotal(new BigDecimal(2585.14));
//        testDao.createNewOrder(testOrder);
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
        Order testOrder = new Order("Test Customer", "Washington", "Wood",
                new BigDecimal(150));
        testOrder.setOrderDate(LocalDate.now().plusDays(30000));
        testOrder.setOrderNumber(14);
        testDao.deleteExistingOrder(testOrder);
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
        testDao = new DaoImpl();
        Order testOrder = new Order("Test Customer", "Washington", "Wood",
                new BigDecimal(150));
        testOrder.setOrderDate(LocalDate.now().plusDays(30000));
        testOrder.setOrderNumber(14);
        testOrder.setCustomerName("Test");
        testOrder.setStateAbbrev("WA");
        testOrder.setTaxRate(new BigDecimal(4.45));
        testOrder.setProductType("Wood");
        testOrder.setArea(new BigDecimal(300));
        testOrder.setCostPerSqFt(new BigDecimal(5.15));
        testOrder.setLaborCostPerSqFt(new BigDecimal(4.75));
        testOrder.setMaterialCost(new BigDecimal(1287.50));
        testOrder.setLaborCost(new BigDecimal(1187.50));
        testOrder.setTax(new BigDecimal(110.14));
        testOrder.setTotal(new BigDecimal(2585.14));
        testDao.createNewOrder(testOrder);
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
        Order testOrder = new Order("Test Customer", "Washington", "Wood",
                new BigDecimal(150));
        testOrder.setOrderDate(LocalDate.now().plusDays(30000));
        testOrder.setOrderNumber(14);
        testDao.deleteExistingOrder(testOrder);
    }

    @org.junit.jupiter.api.Test
    public void testGetAllProducts() throws Exception {
        List<Product> testProductList = testDao.getAllProducts();
        Assertions.assertTrue(testProductList.size() > 0);
    }

    @org.junit.jupiter.api.Test
    public void testGetAllTaxInfo() throws Exception {
        List<TaxInfo> testTaxInfoList = testDao.getAllTaxInfo();
        Assertions.assertTrue(testTaxInfoList.size() > 0);
    }

    @org.junit.jupiter.api.Test
    public void testGetAllOrderInfo() throws Exception {
        List<Order> testOrderList = testDao.getAllOrders();
        Assertions.assertTrue(testOrderList.size() > 0);
    }

    @org.junit.jupiter.api.Test
    public void testGetProduct() throws Exception {
        Product garbageProduct = testDao.getProduct("Garbage");
        Product testProduct = testDao.getProduct("Wood");
        Assertions.assertNull(garbageProduct);
        Assertions.assertNotNull(testProduct);
    }

    @org.junit.jupiter.api.Test
    public void testTaxInfo() throws Exception {
        TaxInfo garbageTaxInfo = testDao.getTaxInfo("Garbage");
        TaxInfo testTaxInfo = testDao.getTaxInfo("WA");
        Assertions.assertNull(garbageTaxInfo);
        Assertions.assertNotNull(testTaxInfo);
    }

    @org.junit.jupiter.api.Test
    public void testGetOrderNumber() throws Exception {
        Set<Integer> testOrderSet = testDao.getOrderNumbers();
        Assertions.assertTrue(testOrderSet.contains(14));
    }

    @org.junit.jupiter.api.Test
    public void testCreateOrder() throws Exception {
        Order testOrder = new Order("Another TestCustomer", "Washington", "Wood",
                new BigDecimal(150));
        testOrder.setOrderDate(LocalDate.now().plusDays(30000));
        testOrder.setOrderNumber(16);
        testOrder.setCustomerName("Test");
        testOrder.setStateAbbrev("WA");
        testOrder.setTaxRate(new BigDecimal(4.45));
        testOrder.setProductType("Wood");
        testOrder.setArea(new BigDecimal(300));
        testOrder.setCostPerSqFt(new BigDecimal(5.15));
        testOrder.setLaborCostPerSqFt(new BigDecimal(4.75));
        testOrder.setMaterialCost(new BigDecimal(1287.50));
        testOrder.setLaborCost(new BigDecimal(1187.50));
        testOrder.setTax(new BigDecimal(110.14));
        testOrder.setTotal(new BigDecimal(2585.14));
        testDao.createNewOrder(testOrder);
        Assertions.assertTrue(testDao.getAllOrders()
                .stream()
                .filter(order -> order.getOrderNumber() == 16)
                .findFirst().isPresent());
    }

    @org.junit.jupiter.api.Test
    public void testUpdateOrder() throws Exception {
        Order testOrder = new Order("Another TestCustomer", "Texas", "Wood",
                new BigDecimal(150));
        testOrder.setOrderDate(LocalDate.now().plusDays(30000));
        testOrder.setOrderNumber(16);
        testOrder.setCustomerName("Test");
        testOrder.setState("WA");
        testOrder.setTaxRate(new BigDecimal(4.45));
        testOrder.setProductType("Wood");
        testOrder.setArea(new BigDecimal(300));
        testOrder.setCostPerSqFt(new BigDecimal(5.15));
        testOrder.setLaborCostPerSqFt(new BigDecimal(4.75));
        testOrder.setMaterialCost(new BigDecimal(1287.50));
        testOrder.setLaborCost(new BigDecimal(1187.50));
        testOrder.setTax(new BigDecimal(110.14));
        testOrder.setTotal(new BigDecimal(2585.14));
        testDao.createNewOrder(testOrder);
        testOrder.setCustomerName("Updated TestName");
        testDao.updateExistingOrder(testOrder);
        Assertions.assertTrue(testDao.getAllOrders()
                .stream()
                .filter(order -> order.getOrderNumber() == 16)
                .findFirst().get().getCustomerName().equals("Updated TestName"));

    }
    @org.junit.jupiter.api.Test
    public void testDeleteOrder() throws Exception {
        Order testOrder = new Order("Test Customer", "Washington", "Wood",
                new BigDecimal(150));
        testOrder.setOrderDate(LocalDate.now().plusDays(30000));
        testOrder.setOrderNumber(14);
        testDao.deleteExistingOrder(testOrder);
        Assertions.assertTrue(testDao.getAllOrders()
                .stream()
                .filter(order -> order.getOrderNumber() == 14)
                .findFirst().isEmpty());
    }

}
