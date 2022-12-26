package api.local.managebd.service.impl;

import java.text.ParseException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.local.managebd.dto.BaseResponse;
import api.local.managebd.dto.JapanFilesDto;
import api.local.managebd.dto.Response;
import api.local.managebd.enums.EnumActions;
import api.local.managebd.enums.EnumStatusError;
import api.local.managebd.model.JapanFiles;
import api.local.managebd.repository.JapanFilesRepository;
import api.local.managebd.service.IJapanFilesService;
import api.local.managebd.utils.DateUtils;
import lombok.extern.java.Log;

@Log
@Service
public class JapanFilesService implements IJapanFilesService {
	
	@Autowired
	private JapanFilesRepository japanFilesRepository;
	
	@Autowired
	private DateUtils dateUtils;

	public JapanFilesDto getJapanFilesByNumPart(final String numPart) {
		log.info("get japan file by num_part in JapanFilesService");
		JapanFiles japan = this.japanFilesRepository.findByNumPart(numPart);
		return this.convertEntityToDto(japan);
	}
	
	public JapanFilesDto getJapanFilesByfile(final String file) {
		log.info("get japan file by file in JapanFilesService");
		JapanFiles japan = this.japanFilesRepository.findByFile(file);
		return this.convertEntityToDto(japan);
	}
	
	public BaseResponse<?> insertapanFiles(final JapanFilesDto japanFilesDto, final String type) throws ParseException {
		log.info("insert japan file in JapanFilesService");
		if(validData(japanFilesDto)) {
			return new Response<>().response(japanFilesDto, EnumStatusError.INCOMPLETE_DATA.getType().concat(dataIncomplete(japanFilesDto)),
					EnumStatusError.INCOMPLETE_DATA.getValue(), type);
		} else {
			JapanFiles japanFiles = this.japanFilesRepository.findByNumPart(japanFilesDto.getNum_part());
			if(japanFiles == null) {
				japanFiles = new JapanFiles();
				Long id = this.japanFilesRepository.count() + 1;
				japanFiles.setId(id);
				japanFiles.setBstate(1);
				BeanUtils.copyProperties(japanFilesDto, japanFiles);
				japanFiles.setDate_creation(dateUtils.parseDate());
				japanFiles.setNumPart(japanFilesDto.getNum_part());
				japanFiles.setObs(dateUtils.transformObs(EnumActions.POST.getAction()));
				JapanFilesDto japanDto = this.convertEntityToDto(this.japanFilesRepository.save(japanFiles));
				return new Response<>().response(japanDto, EnumStatusError.SUCCESS.getType(), EnumStatusError.SUCCESS.getValue(), type);
			}
			return new Response<>().response(japanFilesDto, EnumStatusError.RECORD_ALREADY_EXIST.getType(), EnumStatusError.RECORD_ALREADY_EXIST.getValue(), type);
		}
	}
	
	private boolean validData(final JapanFilesDto japanFilesDto) {
		if((japanFilesDto.getNum_part() == null || japanFilesDto.getNum_part().equals("")) ||
		   japanFilesDto.getLabor_time() == null || 
		   (japanFilesDto.getModel() == null || japanFilesDto.getModel().equals("")) ||
		   (japanFilesDto.getFile() == null || japanFilesDto.getFile().equals(""))) {
			return true;
		} else {
			return false;
		}
	}
	
	private JapanFilesDto convertEntityToDto(final JapanFiles data) {
		JapanFilesDto japanFiles = null;
		if(data != null) {
			japanFiles = new JapanFilesDto();
			BeanUtils.copyProperties(data, japanFiles);
			japanFiles.setNum_part(data.getNumPart());
		}
		return japanFiles;
	}
	
	private String dataIncomplete(final JapanFilesDto japanFilesDto) {
		StringBuilder sb = new StringBuilder();
		if(japanFilesDto.getNum_part() == null || japanFilesDto.getNum_part().equals("")) {
			sb.append(" num_part ");
		}
		if(japanFilesDto.getLabor_time() == null) {
			sb.append(" labor_time" );
		}
		if(japanFilesDto.getModel() == null || japanFilesDto.getModel().equals("")) {
			sb.append(" model" );
		}
		
		if(japanFilesDto.getFile() == null || japanFilesDto.getFile().equals("")) {
			sb.append(" file ");
		}
		return sb.toString();
	}
}
