package api.local.managebd.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Data;

@Data
@RedisHash("Sales")
public class Sales implements Serializable {

	private static final long serialVersionUID = 6610120619324651338L;
	
	private Long id;
	@Indexed
	private String vin;
	private String num_dealer;
	private String name_dealer;
	private Date date_invoice;
	private String model;
	private String nomotor;
	private String obs;
	private Date date_creation;
	private Date date_update;
	private int bstate;

}
