package api.local.managebd.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateUtils {
	
	public String transformObs(final String action) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
		String obs = simpleDateFormat.format(new Date());
		return obs.concat(" ").concat(action);
	}
	
	public Date parseDate() throws ParseException {
		String pattern = "dd/MM/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String dateFormatt = simpleDateFormat.format(new Date());
		Date date = simpleDateFormat.parse(dateFormatt);
		return date;
	}

}
