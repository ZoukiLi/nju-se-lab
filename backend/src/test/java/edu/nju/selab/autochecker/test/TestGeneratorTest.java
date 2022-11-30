package edu.nju.selab.autochecker.test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestGeneratorTest {

        @Test
        void generateOneTest() {
            var seed = System.currentTimeMillis();
            var gen = new TestGenerator("int(0,9) string(1,9) char", 1, seed);
            var test = gen.generateTests().tests().get(0);
            var splits = test.split("\\s+");
            for (var split : splits) {
                System.out.println(split);
            }
            assertEquals(3, splits.length);
            assertTrue(splits[0].matches("[0-9]"));
            assertTrue(splits[1].matches("[a-zA-Z]{1,9}"));
            assertTrue(Character.isLetter(splits[1].charAt(0)));
        }
        @Test
        void generateMultilineTest() {
            var seed = System.currentTimeMillis();
            var gen = new TestGenerator("int(0,9)\nstring(1,9)\nchar", 1, seed);
            var test = gen.generateTests().tests().get(0);
            var splits = test.split("\\r?\\n");
            for (var split : splits) {
                System.out.println(split);
            }
            assertEquals(3, splits.length);
            assertTrue(splits[0].matches("[0-9]"));
            assertTrue(splits[1].matches("[a-zA-Z]{1,9}"));
            assertTrue(Character.isLetter(splits[1].charAt(0)));
        }
        @Test
        void generateBatchTest() {
            var seed = System.currentTimeMillis();
            var size = 1000;
            var gen = new TestGenerator("int(0,9) string(1,9) char", size, seed);
            var batch = gen.generateTests();
            assertEquals(size, batch.tests().size());
            for (var test : batch.tests()) {
                var splits = test.split("\\s+");
                assertEquals(3, splits.length);
                assertTrue(splits[0].matches("[0-9]"));
                assertTrue(splits[1].matches("[a-zA-Z]{1,9}"));
                assertTrue(Character.isLetter(splits[1].charAt(0)));
            }
        }
}