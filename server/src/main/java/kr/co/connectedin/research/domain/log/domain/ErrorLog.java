package kr.co.connectedin.research.domain.log.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "TBL_LOG_ERROR")
public class ErrorLog {

    @Id
    @Column(name = "id_log_error")
    private int logId;

    @Column(name = "var_loginid")
    private String loginId;

    @Column(name = "sys_error")
    private Integer errorCode;

    @Column(name = "date_log")
    private String logDate;

    @Column(name = "var_content")
    private String content;

    @Column(name = "id_menu")
    private String menuId;

    @Column(name = "var_ip")
    private String ip;

    @Builder
    public ErrorLog(int logId, String loginId, Integer sysError, String logDate, String content, String menuId, String ip) {
        this.logId = logId;
        this.loginId = loginId;
        this.errorCode = sysError;
        this.content = content;
        this.logDate = logDate;
        this.menuId = menuId;
        this.ip = ip;
    }
}
