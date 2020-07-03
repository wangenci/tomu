package cn.lefer.tomu.controller;

import cn.lefer.tomu.cache.OnlineStatus;
import cn.lefer.tomu.dto.ChannelStatusDTO;
import cn.lefer.tomu.dto.SongDTO;
import cn.lefer.tomu.exception.BizErrorCode;
import cn.lefer.tomu.exception.BizRestException;
import cn.lefer.tomu.service.ChannelService;
import cn.lefer.tomu.utils.TomuUtils;
import cn.lefer.tomu.view.ChannelView;
import cn.lefer.tomu.view.PlayStatusView;
import cn.lefer.tomu.view.SongView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/16
 * @Description : 频道API
 */
@RestController
@RequestMapping(value = "/api/v1/channel")
public class ChannelController {

    ChannelService channelService;
    OnlineStatus onlineStatus;

    //创建频道
    @PostMapping(value ="")
    public ChannelView createChannel(){
        return channelService.createChannel();
    }

    //获取频道信息
    @GetMapping(value = "/{channelID}")
    public ChannelView getChannel(@PathVariable("channelID") @Validated int channelID) {
        if (channelID == -1) throw new BizRestException(BizErrorCode.CHANNEL_IS_FULL);
        ChannelView channelView = channelService.getChannel(channelID);
        if(channelView==null) throw new BizRestException(BizErrorCode.CHANNEL_NOT_EXISTS);
        return channelView;
    }

    //获取频道下的歌单
    //todo:分页
    @GetMapping(value = "/{channelID}/songs")
    public List<SongView> getSongs(@PathVariable("channelID") @Validated int channelID) {
        return channelService.getSongs(channelID);
    }

    @PostMapping(value = "/{channelID}/song",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public SongView addSong(@PathVariable("channelID") @Validated int channelID, @Validated SongDTO songDTO) {
        return channelService.addSong(channelID,
                songDTO.getSongName(),
                songDTO.getArtistName(),
                songDTO.getCoverUrl(),
                songDTO.getLrcUrl(),
                songDTO.getMp3Url(),
                songDTO.getSongDuration(),
                songDTO.getSongSource(),
                songDTO.getSongUrl());
    }

    /*
    * 状态变化：用户切换歌曲
    */
    @PostMapping(value = "/{channelID}/status",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public boolean changeChannelStatus(@PathVariable("channelID") @Validated int channelID,
                                       @Validated ChannelStatusDTO channelStatusDTO,
                                       ServerWebExchange exchange) {
        return channelService.changeChannelStatus(channelID,channelStatusDTO.getSongID(),channelStatusDTO.getPosition(), TomuUtils.getToken(exchange));
    }

    @GetMapping(value = "/{channelID}/status")
    public Flux<ServerSentEvent<PlayStatusView>> getStatus(@PathVariable("channelID") @Validated int channelID,
                                                           @RequestParam @Validated String clientID,
                                                           ServerWebExchange exchange) {
        return Flux.interval(Duration.ofSeconds(1))
                .filter(l -> channelService.isChannelStatusChanged(channelID,clientID))
                .map(seq -> Tuples.of(seq, ThreadLocalRandom.current().nextInt()))
                .map(data -> (ServerSentEvent.<PlayStatusView>builder()
                        .event("status")
                        .id(Long.toString(data.getT1()))
                        .data(channelService.getNewPlayStatus(channelID,clientID))
                        .build()));
    }

    @GetMapping(value = "/{channelID}/audience")
    public List<String> getAudience(@PathVariable("channelID") @Validated int channelID){
        return onlineStatus.getAudience(channelID);
    }

    @Autowired
    public void setChannelService(ChannelService channelService) {
        this.channelService = channelService;
    }

    @Autowired
    public void setOnlineStatus(OnlineStatus onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
}
