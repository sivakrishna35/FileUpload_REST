package com.mouri.file.upload.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mouri.file.upload.dto.ResponseFileDto;
import com.mouri.file.upload.dto.ResponseMessageDto;
import com.mouri.file.upload.entity.FileDB;
import com.mouri.file.upload.service.FileStorageService;

@RestController
public class FileController {

	@Autowired
	private FileStorageService fileStorageService;

	@PostMapping("/upload")
	public ResponseEntity<ResponseMessageDto> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = "";
		try {
			fileStorageService.store(file);
			message = "Uploaded the file successfully:" + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessageDto(message));
		} catch (IOException e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessageDto(message));
		}

	}

	@GetMapping("/files")
	public ResponseEntity<List<ResponseFileDto>> getListFiles() {
		List<ResponseFileDto> files = fileStorageService.getAllFiles().map(dbFile -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/").path(String.valueOf(dbFile.getId())).toUriString();
			
			return new ResponseFileDto(dbFile.getName(), fileDownloadUri, dbFile.getType(), dbFile.getData().length);
		}).collect(Collectors.toList());
				
			

		return ResponseEntity.status(HttpStatus.OK).body(files);
	}
	
	@GetMapping("/files/{id}")
	  public ResponseEntity<byte[]> getFile(@PathVariable int id) {
	    FileDB fileDB = fileStorageService.getFile(id);

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
	        .body(fileDB.getData());
	  }

}
