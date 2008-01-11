/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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

import org.seasar.mai.annotation.Bcc;
import org.seasar.mai.annotation.Cc;
import org.seasar.mai.annotation.From;
import org.seasar.mai.annotation.MailAddr;
import org.seasar.mai.annotation.ReplyTo;
import org.seasar.mai.annotation.ReturnPath;
import org.seasar.mai.annotation.Subject;
import org.seasar.mai.annotation.To;

/**
 * @author rokugen
 */
@Subject("件名1")
@From(@MailAddr(address="from1@address",personal="FROM1名前"))
@To({@MailAddr(address="to1@address",personal="TO1送信先"),@MailAddr(address="to2@address")})
@Cc(@MailAddr(address="cc1@address",personal="CC1送信先"))
@Bcc({"bcc1@address","bcc2@address"})
@ReplyTo(@MailAddr(address="replyTo1@address",personal="REPLYTO1名前"))
@ReturnPath("returnPath1@address")
public interface TigerTestMai {
	
    void sendMail(TigerTestDto dto);
    
    @Subject("件名2")
    @From(@MailAddr(address="from2@address",personal="FROM2名前"))
    @To(@MailAddr(address="to3@address",personal="TO3送信先"))
    @Cc({@MailAddr(address="cc2@address",personal="CC2送信先"),
    	@MailAddr(address="cc3@address")})        
    @Bcc("bcc3@address")
    @ReplyTo(@MailAddr(address="replyTo2@address",personal="REPLYTO2名前"))
    @ReturnPath("returnPath2@address")    
    void sendMail2(TigerTestDto dto);
	

}
