package api.local.managebd.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Data;

@Data
@RedisHash("Model" )
public class Model implements Serializable {

	private static final long serialVersionUID = 1L;

    private Long id;
    @Indexed
	private String model;
	private Long anio;
	private String color;
	private String description;
	private BigDecimal cchp;
	private String vds;
	private String origin;
	private String obs;
	private Date date_creation;
	private Date date_update;
	private Integer bstate;
}
