package com.project.web_prj.util;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    @Test
    void UUIDTest() {
        for (int i = 0; i < 20; i++) {
            System.out.println(UUID.randomUUID().toString());
        }
    }

}