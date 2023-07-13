package kr.co.connectedin.research.domain.log.service;

import kr.co.connectedin.research.domain.log.dao.ErrorLogRepository;
import kr.co.connectedin.research.domain.log.dao.UseLogFactory;
import kr.co.connectedin.research.domain.log.dao.UseLogRepository;
import kr.co.connectedin.research.domain.log.domain.ErrorLog;
import kr.co.connectedin.research.domain.log.domain.UseLog;
import kr.ac.yonsei.maist.domain.log.dto.*;
import kr.co.connectedin.research.domain.user.dto.UserInformationDto;
import kr.co.connectedin.research.domain.log.dto.ErrorLogListResponseDto;
import kr.co.connectedin.research.domain.log.dto.UseLogListResponseDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LogService {

    @NonNull
    private final UseLogRepository useLogRepository;

    @NonNull
    private final ErrorLogRepository errorLogRepository;

    @Autowired
    private UseLogFactory useLogFactory;

    @Transactional(readOnly = true)
    public List<UseLogListResponseDto> findUseLogByUserId(ArrayList loginId) throws Exception {
        List<UseLogListResponseDto> useLogList = useLogRepository
                .findByLoginIdIn(loginId)
                .stream()
                .map(UseLogListResponseDto::new)
                .collect(Collectors.toList());

        return useLogList;
    }

    @Transactional(readOnly = true)
    public List<ErrorLogListResponseDto> findErrorLogByUserId(ArrayList userId) throws Exception {
        List<ErrorLogListResponseDto> errorLogList = errorLogRepository
                .findByLoginIdIn(userId)
                .stream()
                .map(ErrorLogListResponseDto::new)
                .collect(Collectors.toList());

        return errorLogList;
    }

    @Transactional
    public UseLog createUseLog(String menuID, UserInformationDto dto, String ip, String content) throws Exception {
        UseLog log = UseLog.builder().menuId(menuID).loginId(dto.getLoginId()).ip(ip).content(content).build();
        UseLog useLog = useLogRepository.save(log);

        return useLog;
    }

    @Transactional
    public ErrorLog createErrorLog(String menuID, UserInformationDto dto, String ip, String content) throws Exception {
        ErrorLog log = ErrorLog.builder().menuId(menuID).loginId(dto.getLoginId()).ip(ip).content(content).build();
        ErrorLog errorLog = errorLogRepository.save(log);

        return errorLog;
    }
}
