package net;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetAddress;


/*
Assignment number : 4
File Name : DNSResolverTest.java
Name : Ilay Serr
Email : ilay92@gmail.com
*/

public class DNSResolverTest {
    final static String TEST_HOST = "ap.idc.ac.il";
    final static byte[] NS_ADDR = {8,8,8,8};


    DNSResolver asker;

    @Before
    public void setup() {
        asker = new DNSResolver();
    }

    @Test
    public void testQuery() throws IOException {
        InetAddress NAMESERVER = InetAddress.getByAddress(NS_ADDR);

        InetAddress expected = InetAddress.getByName(TEST_HOST);

        InetAddress actual = asker.queryDNS(TEST_HOST, NAMESERVER);

        assertEquals(expected, actual);
    }

}
