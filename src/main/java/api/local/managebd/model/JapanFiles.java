package api.local.managebd.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Data;

@Data
@RedisHash("JapanFiles")
public class JapanFiles implements Serializable {

	private static final long serialVersionUID = -5299527846641269571L;

	private Long id;
	@Indexed
	private String file;
	@Indexed
	private String numPart;
	private BigDecimal labor_time;
	private String model;
	private String obs;
	private Date date_creation;
	private Date date_update;
	private String description;
	private int bstate;
}
