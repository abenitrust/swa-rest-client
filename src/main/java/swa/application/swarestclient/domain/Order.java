package swa.application.swarestclient.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	@Id
	private String orderNumber;
	private Customer customer;
	private List<OrderLine> orderLineList;

}
