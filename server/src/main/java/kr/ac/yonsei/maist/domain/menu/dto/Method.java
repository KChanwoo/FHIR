/**
 * http method enum
 * @author Chanwoo Gwon, Yonsei Univ. Researcher, since 2020.05~
 * @Date 2021.02.02
 */
package kr.ac.yonsei.maist.domain.menu.dto;

public enum Method {
    PAGE(0),
    GET(1),
    POST(2),
    PUT(3),
    DELETE(4);

    public int val;
    Method(int val) {
        this.val = val;
    }
}
