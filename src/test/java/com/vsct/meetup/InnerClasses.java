package com.vsct.meetup;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * ~  Copyright (C) 2016 VSCT
 * ~
 * ~  Licensed under the Apache License, Version 2.0 (the "License");
 * ~  you may not use this file except in compliance with the License.
 * ~  You may obtain a copy of the License at
 * ~
 * ~   http://www.apache.org/licenses/LICENSE-2.0
 * ~
 * ~  Unless required by applicable law or agreed to in writing, software
 * ~  distributed under the License is distributed on an "AS IS" BASIS,
 * ~  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * ~  See the License for the specific language governing permissions and
 * ~  limitations under the License.
 * ~
 */
public class InnerClasses {

    public static void main(String[] args) {

        List<String> words = new ArrayList<>();

        words.removeIf(new Predicate<String>() {
            @Override
            public boolean test(String word) {
                return word.length() > 8;
            }
        });

        int maxLength = 8;
        words.removeIf(new Predicate<String>() {
            @Override
            public boolean test(String word) {
                return word.length() > maxLength;
            }
        });
    }

}
