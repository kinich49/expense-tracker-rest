package mx.kinich49.expensetracker.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mx.kinich49.expensetracker.models.database.Category;
import mx.kinich49.expensetracker.models.database.PaymentMethod;
import mx.kinich49.expensetracker.models.database.Store;
import mx.kinich49.expensetracker.models.database.Transaction;
import mx.kinich49.expensetracker.models.web.CategoryWebModel;
import mx.kinich49.expensetracker.models.web.PaymentMethodWebModel;
import mx.kinich49.expensetracker.models.web.StoreWebModel;
import mx.kinich49.expensetracker.models.web.TransactionWebModel;
import mx.kinich49.expensetracker.models.web.requests.CategoryRequest;
import mx.kinich49.expensetracker.models.web.requests.PaymentMethodRequest;
import mx.kinich49.expensetracker.models.web.requests.StoreRequest;
import mx.kinich49.expensetracker.models.web.requests.TransactionRequest;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MockHelper {

    private TransactionRequest transactionRequest;
    private Transaction postPersistTransaction;
    private Transaction prePersistTransaction;
    private TransactionWebModel transactionWebModel;
    private Long transactionId;
    private int transactionAmount;
    private String transactionTitle;
    private String transactionMemo;
    private LocalDateTime transactionDate;

    private CategoryRequest categoryRequest;
    private Category postPersistCategory;
    private Category prePersistCategory;
    private CategoryWebModel categoryWebModel;
    private Long categoryId;
    private String categoryName;
    private String categoryColor;

    private StoreRequest storeRequest;
    private Store postPersistStore;
    private Store prePersistStore;
    private StoreWebModel storeWebModel;
    private Long storeId;
    private String storeName;

    private PaymentMethodRequest paymentMethodRequest;
    private PaymentMethod postPersistPaymentMethod;
    private PaymentMethod prePersistPaymentMethod;
    private PaymentMethodWebModel paymentMethodWebModel;
    private Long paymentMethodId;
    private String paymentMethodName;


    public static MockHelper init() {
        return new MockHelper();
    }


    public MockHelper withValidTransactions(LocalDateTime transactionDate) {
        MockPreconditions.validateCategory(this);
        MockPreconditions.validatePayment(this);
        MockPreconditions.validateStore(this);

        transactionAmount = 10000;
        transactionTitle = "Test Transaction";
        transactionMemo = "Transaction memo";
        transactionId = 999L;

        if (transactionDate == null) {
            this.transactionDate = LocalDateTime.now();
        } else {
            this.transactionDate = transactionDate;
        }

        transactionRequest = new TransactionRequest(transactionTitle, transactionMemo,
                transactionAmount, paymentMethodRequest, transactionDate, storeRequest, categoryRequest);

        postPersistTransaction = new Transaction();
        postPersistTransaction.setId(transactionId);
        postPersistTransaction.setTransactionDate(transactionDate);
        postPersistTransaction.setTitle(transactionTitle);
        postPersistTransaction.setMemo(transactionMemo);
        postPersistTransaction.setPaymentMethod(postPersistPaymentMethod);
        postPersistTransaction.setCategory(postPersistCategory);
        postPersistTransaction.setAmount(transactionAmount);
        postPersistTransaction.setStore(postPersistStore);

        prePersistTransaction = new Transaction();
        prePersistTransaction.setTransactionDate(transactionDate);
        prePersistTransaction.setTitle(transactionTitle);
        prePersistTransaction.setMemo(transactionMemo);
        prePersistTransaction.setPaymentMethod(postPersistPaymentMethod);
        prePersistTransaction.setCategory(postPersistCategory);
        prePersistTransaction.setAmount(transactionAmount);
        prePersistTransaction.setStore(null);

        transactionWebModel = new TransactionWebModel(transactionId, transactionTitle, transactionMemo, transactionAmount,
                transactionDate, paymentMethodWebModel, categoryWebModel, storeWebModel);

        return this;
    }

    public MockHelper withValidTransaction() {
        return withValidTransactions(null);
    }

    public MockHelper withInvalidTransaction() {
        throw new IllegalStateException("Not supported");
    }

    public MockHelper withNewCategory() {
        categoryId = 899L;
        categoryName = "New Test Category";
        categoryColor = "#202020";

        categoryRequest = new CategoryRequest(null, categoryName, categoryColor);

        prePersistCategory = new Category();
        prePersistCategory.setName(categoryName);
        prePersistCategory.setColor(categoryColor);

        postPersistCategory = new Category();
        postPersistCategory.setId(categoryId);
        postPersistCategory.setName(categoryName);
        postPersistCategory.setColor(categoryColor);

        categoryWebModel = new CategoryWebModel(categoryId, categoryName, categoryColor);

        return this;
    }

    public MockHelper withPersistedCategory() {
        categoryId = 800L;
        categoryName = "New Test Category";
        categoryColor = "#202020";

        categoryRequest = new CategoryRequest(categoryId, categoryName, categoryColor);

        prePersistCategory = new Category();
        prePersistCategory.setId(categoryId);
        prePersistCategory.setName(categoryName);
        prePersistCategory.setColor(categoryColor);

        postPersistCategory = new Category();
        postPersistCategory.setId(categoryId);
        postPersistCategory.setName(categoryName);
        postPersistCategory.setColor(categoryColor);

        categoryWebModel = new CategoryWebModel(categoryId, categoryName, categoryColor);

        return this;
    }

    public MockHelper withNewStore() {
        storeName = "New Test Store";
        storeId = 799L;

        storeRequest = new StoreRequest(null, storeName);

        prePersistStore = new Store();
        prePersistStore.setName(storeName);

        postPersistStore = new Store();
        postPersistStore.setId(storeId);
        postPersistStore.setName(storeName);

        storeWebModel = new StoreWebModel(storeId, storeName);

        return this;
    }

    public MockHelper withPersistedStore() {
        storeName = "Persisted Test Store";
        storeId = 700L;

        storeRequest = new StoreRequest(storeId, storeName);

        prePersistStore = new Store();
        prePersistStore.setId(storeId);
        prePersistStore.setName(storeName);

        postPersistStore = new Store();
        postPersistStore.setId(storeId);
        postPersistStore.setName(storeName);

        storeWebModel = new StoreWebModel(storeId, storeName);

        return this;
    }

    public MockHelper withNewPaymentMethod() {
        paymentMethodId = 699L;
        paymentMethodName = "New Test Payment Method";

        paymentMethodRequest = new PaymentMethodRequest(null, paymentMethodName);

        prePersistPaymentMethod = new PaymentMethod();
        prePersistPaymentMethod.setName(paymentMethodName);

        postPersistPaymentMethod = new PaymentMethod();
        postPersistPaymentMethod.setId(paymentMethodId);
        postPersistPaymentMethod.setName(paymentMethodName);

        paymentMethodWebModel = new PaymentMethodWebModel(paymentMethodId, paymentMethodName);
        return this;
    }

    public MockHelper withPersistedPaymentMethod() {
        paymentMethodId = 600L;
        paymentMethodName = "Persisted Test Payment Method";

        paymentMethodRequest = new PaymentMethodRequest(paymentMethodId, paymentMethodName);

        prePersistPaymentMethod = new PaymentMethod();
        prePersistPaymentMethod.setId(paymentMethodId);
        prePersistPaymentMethod.setName(paymentMethodName);

        postPersistPaymentMethod = new PaymentMethod();
        postPersistPaymentMethod.setId(paymentMethodId);
        postPersistPaymentMethod.setName(paymentMethodName);

        paymentMethodWebModel = new PaymentMethodWebModel(paymentMethodId, paymentMethodName);
        return this;
    }


}
