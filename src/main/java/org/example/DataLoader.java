package org.example;

import org.example.persistence.model.entity.*;
import org.example.persistence.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader {
    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    private final BusinessRepo businessRepo;
    private final CardRepo cardRepo;
    private final InvestmentRepo investmentRepo;
    private final InvestorRepo investorRepo;
    private final OrderRepo orderRepo;
    private final SimpleUserRepo userRepo;
    private final RoleRepo roleRepo;

    public DataLoader(BusinessRepo businessRepo, CardRepo cardRepo, InvestmentRepo investmentRepo, InvestorRepo investorRepo, OrderRepo orderRepo, SimpleUserRepo userRepo, RoleRepo roleRepo) {
        this.businessRepo = businessRepo;
        this.cardRepo = cardRepo;
        this.investmentRepo = investmentRepo;
        this.investorRepo = investorRepo;
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    public void initDatabase() {
        long now = System.currentTimeMillis();
        logger.info("INITDATABASE: Data Loader started");

        // Инициализация бизнес-модели
        BusinessModel business = new BusinessModel();
        business.setCompanyName("Example Corp");
        business.setCompanyBIN("123456789");
        business.setAddress("123 Business St");
        business.setTypeOfPaymentSystem("Credit Card");
        business.setSector("Technology");
        business.setDateOfBusinessStarted(now);
        business.setDirectorFIO("John Doe1");
        business.setDirectorIIN("123456789012");
        business.setDirectorNumber("+77011234567");
        business.setDirectorMail("contact@example.com");
        business.setCreatedAt(now);
        business.setUpdatedAt(now);
        businessRepo.save(business);

        // Инициализация инвесторов
        InvestorModel investor1 = new InvestorModel();
        investor1.setFio("John Doe2");
        investor1.setIin("123456789");
        investor1.setPhoneNumber("+77011234567");
        investor1.setMail("nurbek@example.com");
        investor1.setInvestorType("individual");
        investor1.setScore(10.0);
        investor1.setIsChecked(true);
        investor1.setAddress("Address 1");
        investor1.setCreatedAt(now);
        investor1.setUpdatedAt(now);

        InvestorModel investor2 = new InvestorModel();
        investor2.setFio("Jane Smith");
        investor2.setIin("987654321");
        investor2.setPhoneNumber("+77019876543");
        investor2.setMail("nurdaulet@example.com");
        investor2.setInvestorType("corporate");
        investor2.setScore(15.0);
        investor2.setIsChecked(true);
        investor2.setAddress("Address 2");
        investor2.setCreatedAt(now);
        investor2.setUpdatedAt(now);

        investorRepo.saveAll(Arrays.asList(investor1, investor2));

        // Инициализация карт
        CardModel card1 = new CardModel();
        card1.setInvestor(investor1);
        card1.setCardNumber("1234 5678 9012 3456");
        card1.setCardholderName("John Doe");
        card1.setExpiryDate("12/25");
        card1.setCreatedAt(now);
        card1.setUpdatedAt(now);

        CardModel card2 = new CardModel();
        card2.setInvestor(investor2);
        card2.setCardNumber("9876 5432 1098 7654");
        card2.setCardholderName("Jane Smith");
        card2.setExpiryDate("11/26");
        card2.setCreatedAt(now);
        card2.setUpdatedAt(now);

        cardRepo.saveAll(Arrays.asList(card1, card2));

        // Инициализация заказов
        OrderModel order = new OrderModel();
        order.setBusiness(business);
        order.setIsActive(true);
        order.setIsAlive(true);
        order.setInvestmentType("EQUITY");
        order.setTargetAmount(10000.00);
        order.setActualAmount(5000.00);
        order.setCurrency("USD");
        order.setDateOfOrder(now);
        order.setDueDate(now + 604800000L); // одна неделя с текущего момента
        order.setPurpose("Business Expansion");
        order.setDescription("Expansion of operations to new regions");
        order.setCollateral("Real Estate");
        orderRepo.save(order);

        // Инициализация инвестиций
        InvestmentModel investment = new InvestmentModel();
        investment.setInvestor(investor1);
        investment.setOrder(order);
        investment.setIsPaid(true);
        investment.setIsActive(false);
        investment.setIsAlive(true);
        investment.setAmount(1000.00);
        investment.setInvestmentDate(now);
        investmentRepo.save(investment);

        RoleModel adminRole = new RoleModel();
        adminRole.setName("ADMIN");
        roleRepo.save(adminRole);

        RoleModel userRole = new RoleModel();
        userRole.setName("USER");
        roleRepo.save(userRole);

        SimpleUser user = new SimpleUser();
        user.setUsername("Nurbek");
        user.setPassword("$2y$10$aICpTR6zQj2kXz/aQbN8n.unrH38198JHIVrQy.MTsb0AIniDexCy"); // Уже зашифрованный пароль

        Set<RoleModel> roles = new HashSet<>();
        roles.add(adminRole);
        roles.add(userRole);

        user.setRoles(roles);
        userRepo.save(user);
    }
}
