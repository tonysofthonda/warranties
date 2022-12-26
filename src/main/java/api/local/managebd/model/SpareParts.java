package api.local.managebd.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Data;

@Data
@RedisHash("SpareParts")
public class SpareParts implements Serializable {

	private static final long serialVersionUID = -4640142933585846521L;
	
	private Long id;
	@Indexed
	private String numPart;
	private BigDecimal price;
	private String model;
	private BigDecimal labor_time;
	private String description;
	private String obs;
	private Date date_creation;
	private Date date_update;
	private int bstate;

}
