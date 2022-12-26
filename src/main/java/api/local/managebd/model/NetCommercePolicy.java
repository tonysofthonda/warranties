package api.local.managebd.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Data;

@Data
@RedisHash("NetCommercePolicy")
public class NetCommercePolicy implements Serializable {

	private static final long serialVersionUID = -3486887591813380424L;

	@Id
	private Long id_guarantee;
	@Indexed
	private Long idHeader;
	private String vin;
	private int folio;
	private int folio_invoice;
	private Date date_invoice;
	private String obs;
	private Date date_creation;
	private Date date_update;
	private int bstate;
	
}
