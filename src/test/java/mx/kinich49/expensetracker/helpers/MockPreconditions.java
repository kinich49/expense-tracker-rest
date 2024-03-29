package mx.kinich49.expensetracker.helpers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MockPreconditions {

    public static void validateCategory(MockHelper.Mock mockHelper) {
        if (mockHelper.getPrePersistCategory() == null ||
                mockHelper.getPostPersistCategory() == null ||
                mockHelper.getCategoryRequest() == null ||
                mockHelper.getCategoryWebModel() == null) {
            throw new RuntimeException("Category is not set");
        }
    }

    public static void validatePayment(MockHelper.Mock mockHelper) {
        if (mockHelper.getPrePersistPaymentMethod() == null ||
                mockHelper.getPostPersistPaymentMethod() == null ||
                mockHelper.getPaymentMethodRequest() == null ||
                mockHelper.getPaymentMethodWebModel() == null) {
            throw new RuntimeException("Payment Method is not set");
        }
    }

    public static void validateStore(MockHelper.Mock mockHelper) {
        if (mockHelper.getPrePersistCommercialEstablishment() == null ||
                mockHelper.getPostPersistCommercialEstablishment() == null ||
                mockHelper.getStoreRequest() == null ||
                mockHelper.getStoreWebModel() == null) {
            throw new RuntimeException("Store is not set");
        }
    }

}
