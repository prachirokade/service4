package com.example.service;

import com.example.annotation.LogMethodParam;
import com.example.entity.Player;
import com.example.entity.PlayerResponse;
import com.example.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author prokade
 */
@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @PostConstruct
    public void init() {
        Player player1 = Player.builder().name("Warrior").id(1).parentId(0).color("red").build();
        Player player2 = Player.builder().name("Wizard").id(2).parentId(0).color("green").build();
        Player player3 = Player.builder().name("Priest").id(3).parentId(0).color("white").build();
        Player player4 = Player.builder().name("Rogue").id(4).parentId(0).color("yellow").build();
        Player player5 = Player.builder().name("Fighter").id(5).parentId(1).color("blue").build();

        Player player6 = Player.builder().name("Paladin").id(6).parentId(1).color("lighblue").build();
        Player player7 = Player.builder().name("Ranger").id(7).parentId(1).color("lighgreen").build();
        Player player8 = Player.builder().name("Mage").id(8).parentId(2).color("grey").build();
        Player player9 = Player.builder().name("Specialist wizard").id(9).parentId(2).color("lightgrey").build();
        Player player10 = Player.builder().name("Cleric").id(10).parentId(3).color("red").build();

        Player player11 = Player.builder().name("Druid").id(11).parentId(3).color("green").build();
        Player player12 = Player.builder().name("Priest of specific mythos").id(12).parentId(3).color("white").build();
        Player player13 = Player.builder().name("Thief").id(13).parentId(4).color("yellow").build();
        Player player14 = Player.builder().name("Bard").id(14).parentId(4).color("blue").build();
        Player player15 = Player.builder().name("Assassin").id(15).parentId(13).color("lighblue").build();
        playerRepository.save(player1);
        playerRepository.save(player2);
        playerRepository.save(player3);
        playerRepository.save(player4);
        playerRepository.save(player5);
        playerRepository.save(player6);
        playerRepository.save(player7);
        playerRepository.save(player8);
        playerRepository.save(player9);
        playerRepository.save(player10);
        playerRepository.save(player11);
        playerRepository.save(player12);
        playerRepository.save(player13);
        playerRepository.save(player14);
        playerRepository.save(player15);
    }

    public List<PlayerResponse> getAllPlayers() {
        Iterator<Player> playerIterator = playerRepository.findAll().iterator();

        List<PlayerResponse> playerResponseList = new ArrayList<>();
        Set<Integer> players = new HashSet<>();
        while (playerIterator.hasNext()) {
            Player player = playerIterator.next();
            if (!players.contains(player.getId())) {

                PlayerResponse playerResponse = PlayerResponse.builder().name(player.getName()).build();
                populateParentPlayers(player, playerResponse, players);
                playerResponseList.add(playerResponse);
            }
        }

        return playerResponseList;
    }

    @LogMethodParam
    public PlayerResponse getPlayerById(int id) {
        Optional<Player> player = playerRepository.findById(id);


        if (player.isPresent()) {
            PlayerResponse playerResponse = PlayerResponse.builder().name(player.get().getName()).build();
            populateParentPlayers(player.get(), playerResponse, new HashSet<>());

            return playerResponse;
        }


        return null;
    }


    public void populateParentPlayers(Player player, PlayerResponse playerResponse, Set<Integer> players) {


        Set<Player> parentPlayers = playerRepository.findByParentId(player.getId());

        if (CollectionUtils.isEmpty(parentPlayers))
            return;

        parentPlayers.stream().forEach(parentPlayer -> {
            players.add(parentPlayer.getId());
            PlayerResponse playerResponse1 = PlayerResponse.builder().name(parentPlayer.getName()).build();
            if (null == playerResponse.getSubclasses()) {
                playerResponse.setSubclasses(new ArrayList<>());
            }
            playerResponse.getSubclasses().add(playerResponse1);
            populateParentPlayers(parentPlayer, playerResponse1, players);
        });


    }

}
