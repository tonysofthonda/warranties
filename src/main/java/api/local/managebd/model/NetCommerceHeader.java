package api.local.managebd.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Data;

@Data
@RedisHash("NetCommerceHeader")
public class NetCommerceHeader implements Serializable {

	private static final long serialVersionUID = -2937762368827626193L;
	
	@Id
	private Long id_header;
	@Indexed
	private String vin;
	private Date date_sale;
	private String nomotor;
	private String model;
	private int year;
	private String color;
	private String num_part;
	private String num_dealer;
	private String name_dealer;
	private String obs;
	private Date date_creation;
	private Date date_update;
	private Date date_invoice;
	private int bstate;
}
