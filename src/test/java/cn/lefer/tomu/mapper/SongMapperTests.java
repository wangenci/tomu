package cn.lefer.tomu.mapper;

import cn.lefer.tomu.constant.SongSource;
import cn.lefer.tomu.constant.SongStatus;
import cn.lefer.tomu.entity.Song;
import cn.lefer.tools.Date.LeferDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/28
 * @Description : song持久化接口测试用例
 */
@SpringBootTest
@Transactional
public class SongMapperTests {
    @Autowired
    private SongMapper songMapper;

    @Test
    public void testInsert() {
        Song song = initSong();
        songMapper.insert(song);
        Assertions.assertTrue(song.getSongID() > 0);
    }

    @Test
    public void testDelete() {
        Song song = initSong();
        songMapper.insert(song);
        Assertions.assertEquals(1, songMapper.deleteByID(song.getSongID()));
    }

    @Test
    public void testSelectByID() {
        Song song = songMapper.selectByID(2);
        System.out.println(song);
        Assertions.assertNotNull(song);
    }

    @Test
    public void testSelectByChannelID() {
        List<SongStatus> songStatusList = new ArrayList<>();
        songStatusList.add(SongStatus.NORMAL);
        songStatusList.add(SongStatus.OUTDATE);
        List<Song> songs = songMapper.selectByChannelID(1, songStatusList, 2, 1);
        System.out.println(songs);
        Assertions.assertTrue(songs.size() > 0);
    }

    @Test
    public void testCountByChannelID() {
        List<SongStatus> songStatusList = new ArrayList<>();
        songStatusList.add(SongStatus.NORMAL);
        songStatusList.add(SongStatus.OUTDATE);
        int total = songMapper.countByChannelID(1, songStatusList);
        System.out.println(total);
        Assertions.assertTrue(total >= 0);
    }

    private Song initSong() {
        Song song = new Song();
        song.setSongUrl("https://music.163.com/#/song?id=1307473639");
        song.setSongSource(SongSource.netease);
        song.setSongName("Car Park");
        song.setSongDuration(180);
        song.setChannelID(1);
        song.setArtistName("Fenne Lily");
        song.setCoverUrl("https://api.i-meto.com/meting/api?server=netease&type=pic&id=109951163105662267&auth=67845de5ba4fff4a715c495ed9f31a9b72ad545b");
        song.setLrcUrl("https://api.i-meto.com/meting/api?server=netease&type=lrc&id=340383&auth=620e461301fd513c8dd0b766bbea22d51f912850");
        song.setMp3Url("https://api.i-meto.com/meting/api?server=netease&type=song&id=340383&r=0.023052520560386425");
        song.setSongStatus(SongStatus.NORMAL);
        song.setSongAddDate(LeferDate.today());
        return song;
    }
}