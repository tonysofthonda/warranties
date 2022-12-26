package api.local.managebd.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Data;

@Data
@RedisHash("NetCommerceCustomer")
public class NetCommerceCustomer implements Serializable {

	private static final long serialVersionUID = -3502392039541942063L;
	
	@Id
	private Long id_customer;
	@Indexed
	private Long idHeader;
	private String vin;
	private String name;
	private String email;
	private String city;
	private String obs;
	private Date date_creation;
	private Date date_update;
	private int bstate;

}
