/**
 * @Author Chanwoo Gwon, Yonsei Univ. Researcher, since 2020.05
 * @Author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2021.01.03
 */
package kr.co.connectedin.research.domain.system.service;

import kr.co.connectedin.research.domain.system.dao.SystemQueryFactory;
import kr.co.connectedin.research.domain.system.dao.SystemRepository;
import kr.co.connectedin.research.domain.system.domain.System;
import kr.co.connectedin.research.domain.system.dto.SystemCreateRequestDto;
import kr.co.connectedin.research.domain.system.dto.SystemListResponseDto;
import kr.co.connectedin.research.domain.system.dto.SystemResponseDto;
import kr.co.connectedin.research.domain.system.dto.SystemUpdateRequestDto;
import kr.co.connectedin.research.global.dto.PagingDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * system code service
 */
@RequiredArgsConstructor
@Service
public class SystemService {

    @NonNull
    private final SystemRepository systemRepository;
    @Autowired
    private SystemQueryFactory systemQueryFactory;

    /**
     * Find all of system code by domain name and depth level.
     * @param domain domain name
     * @param depth1 depth level 1
     * @param depth2 depth level 2
     * @param depth3 depth level 3
     * @return list of system code
     */
    @Transactional
    public List<SystemResponseDto> findAllByDomain(String domain, String depth1, String depth2, String depth3) {
        List<SystemResponseDto> result = systemQueryFactory.findAllBySystemDomain(domain, depth1, depth2, depth3);

        return result;
    }

    /**
     * Find all of domain of system code.
     * @return list of domain of system code
     */
    @Transactional
    public List<SystemResponseDto> findAllDomain() {
        List<SystemResponseDto> result = systemQueryFactory.findAllDomain();

        return result;
    }

    /**
     * Find all of system code.
     * @return list of system code
     */
    @Transactional(readOnly = true)
    public List<SystemListResponseDto> findSystemCode(PagingDto pagingDto) throws Exception {

        Pageable paging = PageRequest.of(pagingDto.getCurrentPageNo()-1, pagingDto.getElementsPerPage());

        List<SystemListResponseDto> systemCodeList = systemRepository
                .findAll(paging)
                .stream()
                .map(SystemListResponseDto::new)
                .collect(Collectors.toList());

        return systemCodeList;
    }

    /**
     * Find count of all system code.
     * @return count
     */
    @Transactional(readOnly = true)
    public long countSystemCode() throws Exception {
        long totalRecordCount = systemRepository.count();

        return totalRecordCount;
    }

    /**
     * Create a system code.
     * @param dto system code information
     */
    @Transactional
    public void createSystemCode(SystemCreateRequestDto dto) throws Exception {

        Optional<System> entity = systemRepository.findBySysCodeId(dto.getSysCodeId());
        if(!entity.isPresent()) {
            systemRepository.save(dto.toEntity());
        } else {
            throw new KeyAlreadyExistsException();
        }
    }

    /**
     * Update system code information.
     * @param sysCodeId system code id
     * @param dto system code information
     */
    @Transactional
    public void updateSystemCode(int sysCodeId, SystemUpdateRequestDto dto) throws Exception {

        System entity = systemRepository.findById(sysCodeId)
                .orElseThrow(() -> new IllegalArgumentException("id="+sysCodeId));

        entity = dto.update(entity);
        systemRepository.save(entity);
    }

    /**
     * Delete a system code.
     * @param sysCodeId system code id
     */
    @Transactional
    public void deleteSystemCode(int sysCodeId) throws Exception {

        System entity = systemRepository.findById(sysCodeId)
                .orElseThrow(() -> new IllegalArgumentException("id="+sysCodeId));

        systemRepository.delete(entity);
    }

    @Transactional(readOnly = true)
    public List<SystemListResponseDto> findSystemByEditDateAfter(String downloadDate) throws Exception {
        List<SystemListResponseDto> systemCodeList = systemRepository
                .findByEditDateAfter(downloadDate)
                .stream()
                .map(SystemListResponseDto::new)
                .collect(Collectors.toList());

        return systemCodeList;
    }

    @Transactional(readOnly = true)
    public int countSystemByEditDateAfter(String downloadDate) throws Exception {
        int totalRecordCount = systemRepository.countByEditDateAfter(downloadDate);

        return totalRecordCount;
    }

    @Transactional(readOnly = true)
    public List<SystemResponseDto> findByDomain(String domain) {
        return systemRepository.findByDomain(domain)
                .stream()
                .map(SystemResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SystemResponseDto> findPositionList() {
        return systemRepository.findByDomain("position")
                .stream()
                .map(SystemResponseDto::new)
                .collect(Collectors.toList());
    }
}
