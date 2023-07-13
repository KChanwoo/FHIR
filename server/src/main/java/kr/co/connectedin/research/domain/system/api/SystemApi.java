/**
 * @Author Chanwoo Gwon, Yonsei Univ. Researcher, since 2020.05
 * @Author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2021.01.03
 */
package kr.ac.yonsei.maist.domain.system.api;

import kr.ac.yonsei.maist.domain.system.dto.SystemCreateRequestDto;
import kr.ac.yonsei.maist.domain.system.dto.SystemListResponseDto;
import kr.ac.yonsei.maist.domain.system.dto.SystemResponseDto;
import kr.ac.yonsei.maist.domain.system.dto.SystemUpdateRequestDto;
import kr.ac.yonsei.maist.domain.system.service.SystemService;
import kr.ac.yonsei.maist.global.dto.PagingDto;
import kr.ac.yonsei.maist.global.response.ResponseMessage;
import kr.ac.yonsei.maist.global.response.dataMessage.GeneralDataMessage;
import kr.ac.yonsei.maist.global.response.dataMessage.PagingDataMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * System code controller
 */
@RequiredArgsConstructor
@RestController
public class SystemApi {

    private final SystemService systemService;

    /**
     * Get list of system code by domain name and depth.
     */
    @GetMapping("/system/{domain}/{depth1}/{depth2}/{depth3}")
    public ResponseEntity<GeneralDataMessage> systemCodeList(
            @PathVariable String domain,
            @PathVariable String depth1,
            @PathVariable String depth2,
            @PathVariable String depth3
    ) throws Exception {
        List<SystemResponseDto> systemCodeList = systemService.findAllByDomain(domain, depth1, depth2, depth3);

        GeneralDataMessage responseMessage = GeneralDataMessage.builder()
                .data(systemCodeList)
                .build();

        return new ResponseEntity<GeneralDataMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * Get list of system code's domain.
     */
    @GetMapping("/system/domain")
    public ResponseEntity<GeneralDataMessage> systemCodeList() throws Exception {
        List<SystemResponseDto> systemCodeList = systemService.findAllDomain();

        GeneralDataMessage responseMessage = GeneralDataMessage.builder()
                .data(systemCodeList)
                .build();

        return new ResponseEntity<GeneralDataMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * Get list of system code (paging).
     * @return ResponseEntity<PagingDataMessage>
     */
    @GetMapping("/system")
    public ResponseEntity<PagingDataMessage> findSystemCode(@Valid PagingDto pagingDto) throws Exception {

        List<SystemListResponseDto> systemCodeList = null;

        long totalRecordCount = systemService.countSystemCode();
        if(totalRecordCount > 0) {
            systemCodeList = systemService.findSystemCode(pagingDto);
        }

        PagingDataMessage responseMessage = PagingDataMessage.builder()
                .totalPages(pagingDto.getTotalPageCount())
                .totalElements(totalRecordCount)
                .elementsPerPage(pagingDto.getElementsPerPage())
                .data(systemCodeList)
                .build();

        return new ResponseEntity<PagingDataMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * Create a system code.
     * @param dto system code information
     * @return ResponseEntity<ResponseMessage>
     */
    @PostMapping("/system")
    public ResponseEntity<ResponseMessage> createSystemCode(@Valid @RequestBody SystemCreateRequestDto dto) throws Exception {

        systemService.createSystemCode(dto);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(), HttpStatus.OK);
    }

    /**
     * Update system code information.
     * @param sysCodeId system code id
     * @param dto system code information
     * @return ResponseEntity<ResponseMessage>
     */
    @PutMapping("/system/{sysCodeId}")
    public ResponseEntity<ResponseMessage> updateSystemCode(@PathVariable int sysCodeId,
                                                      @Valid @RequestBody SystemUpdateRequestDto dto) throws Exception {

        systemService.updateSystemCode(sysCodeId, dto);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(), HttpStatus.OK);
    }

    /**
     * Delete a system code.
     * @param sysCodeId system code id
     * @return ResponseEntity<ResponseMessage>
     */
    @DeleteMapping("/system/{sysCodeId}")
    public ResponseEntity<ResponseMessage> deleteSystemCode(@PathVariable int sysCodeId) throws Exception {

        systemService.deleteSystemCode(sysCodeId);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(), HttpStatus.OK);
    }

    @GetMapping("/system/{domain}")
    public ResponseEntity<GeneralDataMessage> findByDomain(@PathVariable String domain) throws Exception {
        List<SystemResponseDto> dtoList = this.systemService.findByDomain(domain);

        GeneralDataMessage response = GeneralDataMessage.builder().data(dtoList).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
