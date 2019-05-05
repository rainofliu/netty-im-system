package com.ajin.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户端Session
 *
 * @author ajin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    private String userId;
    private String userName;
  @Override
    public String toString() {
        return userId + ":" + userName;
    }
}
