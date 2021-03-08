package com.reikop.aionmini.common.service;

import com.reikop.aionmini.work.model.Item;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class ItemServiceImpl implements ItemService {
    private final JdbcTemplate jdbcTemplate;

    public ItemServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Item> findItem(String value) {
        if(StringUtils.isEmpty(value)){
            return Collections.emptyList();
        }
        String keyword = value.replaceAll("[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]", "");
        String query = "select * from ITEMS WHERE " + getWhere(keyword) + " limit 30";
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Item.class));
    }

    @Override
    public void make() {
        String query = "select * from ITEMS";
        List<Item> data = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Item.class));
        jdbcTemplate.batchUpdate("UPDATE ITEMS SET jaso = ? WHERE id = ?",
                data, 100,
                (ps, argument) -> {
                    ps.setString(1, toJaso(argument.getName()));
                    ps.setInt(2, argument.getId());
                });
    }

    private String toJaso(String value){
        String s = value.replaceAll("\\p{Z}", "").trim();
        char[] ChoSung   = { 0x3131, 0x3132, 0x3134, 0x3137, 0x3138, 0x3139, 0x3141, 0x3142, 0x3143, 0x3145, 0x3146, 0x3147, 0x3148, 0x3149, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };
        char[] JwungSung = { 0x314f, 0x3150, 0x3151, 0x3152, 0x3153, 0x3154, 0x3155, 0x3156, 0x3157, 0x3158, 0x3159, 0x315a, 0x315b, 0x315c, 0x315d, 0x315e, 0x315f, 0x3160, 0x3161, 0x3162, 0x3163 };
        char[] JongSung  = { 0,      0x3131, 0x3132, 0x3133, 0x3134, 0x3135, 0x3136, 0x3137, 0x3139, 0x313a, 0x313b, 0x313c, 0x313d, 0x313e, 0x313f, 0x3140, 0x3141, 0x3142, 0x3144, 0x3145, 0x3146, 0x3147, 0x3148, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };
        int a, b, c; // 자소 버퍼: 초성/중성/종성 순
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch >= 0xAC00 && ch <= 0xD7A3) { // "AC00:가" ~ "D7A3:힣" 에 속한 글자면 분해
                c = ch - 0xAC00;
                a = c / (21 * 28);
                c = c % (21 * 28);
                b = c / 28;
                c = c % 28;

                result.append(ChoSung[a]).append(JwungSung[b]);

                if (c != 0) result.append(JongSung[c]); // c가 0이 아니면, 즉 받침이 있으면
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    private String getWhere(String keyword){
        if(StringUtils.isEmpty(keyword)){
            return " 1=1";
        }
        String[] s = keyword.split(" ");
        List<String> and = new ArrayList<>();
        for (String value : s) {
            and.add(" jaso like '%"+toJaso(value)+"%' ");
        }

        return StringUtils.join(and, " AND ");
    }
}
