package kr.co.connectedin.research.domain.system.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@Setter
@Getter
@Table(name = "TBL_SYSTEM_CODE")
public class System {

    @Id
    @Column(name = "id_sys_code")
    private String sysCodeId;

    @Column(name = "var_domain")
    private String domain;

    @Column(name = "var_name")
    private String name;

    @Column(name = "var_content")
    private String content;

    @Column(name = "int_depth1")
    private int depth1;

    @Column(name = "int_depth2")
    private int depth2;

    @Column(name = "int_depth3")
    private int depth3;

    @Column(name = "date_create")
    private String createDate;

    @Column(name = "date_edit")
    private String editDate;
}
