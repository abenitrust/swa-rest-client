package swa.application.swarestclient.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {
    @Id
    private Long shoppingCartNumber;
    private String customerId;
    private HashMap<Long,ProductDto> cartLines = new HashMap<>();

}
