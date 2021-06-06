package mx.kinich49.expensetracker.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("ALL")
@ExtendWith(MockitoExtension.class)
public class CollectionUtilTest {

    @Test
    @DisplayName("Should return false when collection is null")
    public void shouldReturnFalse_whenCollectionIsNull(){
        //given
        List<Object> list = null;

        //when
        boolean result = CollectionUtils.isNeitherNullNorEmpty(list);

        //then
        assertFalse(result);
    }

    @Test
    @DisplayName("Should return false when collection is empty")
    public void shouldReturnFalse_whenCollectionIsEmpty() {
        //given
        List<Object> list = new ArrayList<>();

        //when
        boolean result = CollectionUtils.isNeitherNullNorEmpty(list);

        //then
        assertFalse(result);
    }

    @Test
    @DisplayName("Should return true when collection is not empty")
    public void shouldReturnTrue_whenCollectionIsNotEmpty() {
        //given
        List<Object> list = new ArrayList<>(1);
        Object object = new Object();
        list.add(object);

        //when
        boolean result = CollectionUtils.isNeitherNullNorEmpty(list);

        //then
        assertTrue(result);
    }
}
