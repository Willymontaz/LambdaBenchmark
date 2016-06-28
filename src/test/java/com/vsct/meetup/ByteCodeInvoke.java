package com.vsct.meetup;

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
public class ByteCodeInvoke {

    interface SimpleInterface {
        default String returnStringFromInterface() {
            return "string";
        }
    }

    static class Stringer implements SimpleInterface {
        public static String returnStringStatic() {
            return "string";
        }

        public String returnStringFromClass() {
            return "string";
        }

        private String returnStringFromClassPrivate() {
            return "string";
        }
    }

    public static void main(String[] args) {
        Stringer.returnStringStatic();
        Stringer stringer = new Stringer();
        stringer.returnStringFromClass();
        stringer.returnStringFromClassPrivate();
        stringer.returnStringFromInterface();
        ((SimpleInterface)stringer).returnStringFromInterface();
    }


}
