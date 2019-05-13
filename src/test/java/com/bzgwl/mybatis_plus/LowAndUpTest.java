package com.bzgwl.mybatis_plus;

import org.junit.Test;

import static com.bzgwl.mybatis_plus.utils.LowAndUpConvert.lowFirst;
import static com.bzgwl.mybatis_plus.utils.LowAndUpConvert.upFirst;

public class LowAndUpTest {

    @Test
    public void Test() {
        String assdff = lowFirst("SSDFF");
        System.out.println(assdff);
    }

    @Test
    public void Test2() {
        String assdff = upFirst("aaaSSDFF");
        System.out.println(assdff);
    }
}
