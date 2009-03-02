/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.mai.interceptors;

/**
 * @author rokugen
 */
public interface AnnotationTestMai {
    String[] TO = new String[] { "kei", "rokugen" };

    String sendMail2_TO = "rokugen";

    String[] sendMail2_CC = new String[] { "kei", "rokugen" };

    String sendMail2_BCC = "rokugen";

    String[] sendMail3_BCC = new String[] { "kei", "rokugen" };

    String sendMail3_REPLY_TO = "rokugen";

    String sendMail2_RETURN_PATH = "kimura";

    String FROM = "takeuchi";

    String sendMail_SUBJECT = "件名置き換えテスト";

    String sendMail2_SUBJECT = "テンプレート優先で無効";

    void sendMail(TestData data);

    void sendMail2(TestData data);

    void sendMail3(TestData data);
}
