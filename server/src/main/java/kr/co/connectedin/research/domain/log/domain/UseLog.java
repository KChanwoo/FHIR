package kr.ac.yonsei.maist.domain.log.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DynamicInsert
@Table(name = "TBL_LOG_USE")
public class UseLog {

    @Id
    @Column(name = "id_log_use")
    private int logId;

    @Column(name = "var_loginid")
    private String loginId;

    @Column(name = "date_log")
    private String logDate;

    @Column(name = "var_content")
    private String content;

    @Column(name = "id_menu")
    private String menuId;

    @Column(name = "var_ip")
    private String ip;

    @Builder
    public UseLog(String loginId,
                  String logDate,
                  String content,
                  String menuId,
                  String ip) {

        this.loginId = loginId;
        this.logDate = logDate;
        this.content = content;
        this.menuId = menuId;
        this.ip = ip;
    }
}
