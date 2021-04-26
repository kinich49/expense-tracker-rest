package mx.kinich49.expensetracker.helpers;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MockHelper {

    private final List<Mock> mocks;

    public static MockHelper init(int capacity) {
        List<Mock> mocks = new ArrayList<>(capacity);
        return new MockHelper(mocks);
    }

    public static MockHelper init() {
        List<Mock> mocks = new ArrayList<>(1);
        return new MockHelper(mocks);
    }

    public MockHelper with(Mock mock) {
        mocks.add(mock);
        return this;
    }

    public Mock get() {
        return mocks.get(0);
    }

    public List<Mock> getAll() {
        return new ArrayList<>(mocks);
    }

    public int size() {
        return mocks.size();
    }

    public Mock get(int index) {
        return mocks.get(0);
    }

    public static Mock addMock() {
        return new Mock();
    }

    public List<Transaction> getTransactions() {
        if (mocks == null || mocks.isEmpty())
            return Collections.emptyList();

        return mocks
                .stream()
                .map(MockHelper.Mock::getPostPersistTransaction)
                .collect(Collectors.toList());
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PACKAGE)
    public static class Mock {

        private TransactionRequest transactionRequest;
        private Transaction postPersistTransaction;
        private Transaction prePersistTransaction;
        private TransactionWebModel transactionWebModel;
        private Long transactionId;
        private int transactionAmount;
        private String transactionTitle;
        private String transactionMemo;
        private LocalDateTime transactionDate;

        private boolean allowNullCategory;
        private CategoryRequest categoryRequest;
        private Category postPersistCategory;
        private Category prePersistCategory;
        private CategoryWebModel categoryWebModel;
        private Long categoryId;
        private String categoryName;
        private String categoryColor;

        private boolean allowNullStore;
        private StoreRequest storeRequest;
        private Store postPersistStore;
        private Store prePersistStore;
        private StoreWebModel storeWebModel;
        private Long storeId;
        private String storeName;

        private boolean allowNullPaymentMethod;
        private PaymentMethodRequest paymentMethodRequest;
        private PaymentMethod postPersistPaymentMethod;
        private PaymentMethod prePersistPaymentMethod;
        private PaymentMethodWebModel paymentMethodWebModel;
        private Long paymentMethodId;
        private String paymentMethodName;

        public Mock withValidTransaction(LocalDateTime transactionDate) {
            if (!allowNullCategory)
                MockPreconditions.validateCategory(this);
            if (!allowNullPaymentMethod)
                MockPreconditions.validatePayment(this);
            if (!allowNullStore)
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
                    transactionAmount, paymentMethodRequest, this.transactionDate, storeRequest, categoryRequest);

            postPersistTransaction = new Transaction();
            postPersistTransaction.setId(transactionId);
            postPersistTransaction.setTransactionDate(this.transactionDate);
            postPersistTransaction.setTitle(transactionTitle);
            postPersistTransaction.setMemo(transactionMemo);
            postPersistTransaction.setPaymentMethod(postPersistPaymentMethod);
            postPersistTransaction.setCategory(postPersistCategory);
            postPersistTransaction.setAmount(transactionAmount);
            postPersistTransaction.setStore(postPersistStore);

            prePersistTransaction = new Transaction();
            prePersistTransaction.setTransactionDate(this.transactionDate);
            prePersistTransaction.setTitle(transactionTitle);
            prePersistTransaction.setMemo(transactionMemo);
            prePersistTransaction.setPaymentMethod(postPersistPaymentMethod);
            prePersistTransaction.setCategory(postPersistCategory);
            prePersistTransaction.setAmount(transactionAmount);
            prePersistTransaction.setStore(null);

            transactionWebModel = new TransactionWebModel(transactionId, transactionTitle, transactionMemo, transactionAmount,
                    this.transactionDate, paymentMethodWebModel, categoryWebModel, storeWebModel);

            return this;
        }

        public Mock withValidTransaction() {
            return withValidTransaction(null);
        }

        public Mock withEmptyTransaction() {
            transactionRequest = new TransactionRequest(null, null,
                    0, null, null, null, null);

            return this;
        }

        public Mock withInvalidTransaction() {
            throw new IllegalStateException("Not supported");
        }

        public Mock withNewCategory() {
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

        public Mock withPersistedCategory() {
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

        public Mock withNullCategory() {
            allowNullCategory = true;
            return this;
        }

        public Mock withNullStore() {
            allowNullStore = true;
            return this;
        }

        public Mock withNullPaymentMethod() {
            allowNullPaymentMethod = true;
            return this;
        }

        public Mock withNewStore() {
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

        public Mock withEmptyStore() {
            storeRequest = new StoreRequest(null, null);
            return this;
        }

        public Mock withEmptyCategory() {
            categoryRequest = new CategoryRequest(null, null, null);
            return this;
        }

        public Mock withEmptyPayment() {
            paymentMethodRequest = new PaymentMethodRequest(null, null);
            return this;
        }

        public Mock withPersistedStore() {
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

        public Mock withNewPaymentMethod() {
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

        public Mock withPersistedPaymentMethod() {
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
}
