/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.konex.netty.netty.handler;


import com.konex.netty.entity.User;
import com.konex.netty.netty.ChannelRepository;
import com.konex.netty.service.NettyServices;
import com.konex.netty.service.UserService;
import com.konex.netty.service.workingWithFiles.WorkingWithFiles;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;

/**
 * event handler to process receiving messages
 *
 * @author Jibeom Jung
 */
@Component
@Qualifier("somethingServerHandler")
@ChannelHandler.Sharable
public class SomethingServerHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private NettyServices nettyServices;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private WorkingWithFiles workingWithFiles;

    private static Logger logger = Logger.getLogger(SomethingServerHandler.class.getName());

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Assert.notNull(this.channelRepository, "[Assertion failed] - ChannelRepository is required; it must not be null");

        ctx.fireChannelActive();
        logger.debug(ctx.channel().remoteAddress());
        String channelKey = ctx.channel().remoteAddress().toString();
        channelRepository.put(channelKey, ctx.channel());
        System.out.println("Your channel key is " + channelKey + "\n\r");
        //ctx.writeAndFlush("Your channel key is " + channelKey + "\n\r");

        logger.debug("Binded Channel Count is " + this.channelRepository.size());
        ctx.channel().writeAndFlush("test");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String stringMessage = (String) msg;
        logger.debug(stringMessage);
        System.out.println(stringMessage);

        String[] splitMessage = stringMessage.split("::");

        if ( splitMessage.length != 2 ) {
            for(Channel chenel : channelRepository.getAllchannelCache().values()) {
                User user=null;//userService.getUserById(Long.valueOf(stringMessage));
                nettyServices.takeObject(msg);
                if(user!=null){
                    System.out.println(user.getUserPIB());
                    chenel.writeAndFlush(ctx.channel().remoteAddress().toString()+" :"+stringMessage+" "+user.getUserPIB()+"\n\r");
                }else{
                    chenel.writeAndFlush(ctx.channel().remoteAddress().toString()+" :"+stringMessage+" "+"\n\r");
//                    if (stringMessage.equals("read")){
//                        workingWithFiles.getFileContent("fn");
//                    }else{
//                        workingWithFiles.writeFile(stringMessage);
//                    }
                }

            }
            //ctx.channel().writeAndFlush("Server :"+stringMessage + "\n\r");
            return;
        }

        if ( channelRepository.get(splitMessage[0]) != null ) {
            channelRepository.get(splitMessage[0]).writeAndFlush(splitMessage[1] + "\n\r");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getMessage(), cause);
        //ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        Assert.notNull(this.channelRepository, "[Assertion failed] - ChannelRepository is required; it must not be null");
        Assert.notNull(ctx);

        String channelKey = ctx.channel().remoteAddress().toString();
        this.channelRepository.remove(channelKey);

        logger.debug("Binded Channel Count is " + this.channelRepository.size());
    }

    public void setChannelRepository(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }
}
