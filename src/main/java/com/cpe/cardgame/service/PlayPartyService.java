package com.cpe.cardgame.service;

import com.cpe.cardgame.model.PlayParty;
import com.cpe.cardgame.model.StoreOrder;
import com.cpe.cardgame.repositories.CardRepository;
import com.cpe.cardgame.repositories.PlayPartyRepository;
import com.cpe.cardgame.repositories.StoreOrderRepository;
import com.cpe.cardgame.repositories.UserRepository;
import com.cpe.cardgame.utils.ResponseCode;
import com.cpe.cardgame.utils.ResponseMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayPartyService {

    private final PlayPartyRepository storeOrderRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    public PlayPartyService(
            PlayPartyRepository storeOrderRepository,
            CardRepository cardRepository,
            UserRepository userRepository
    ) {
        this.storeOrderRepository = storeOrderRepository;
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    // Example method for updating a user
    public ResponseMessage<PlayParty> updatePlayParty(PlayParty storeOrder) {
        var result =  storeOrderRepository.save(storeOrder);
        ResponseMessage<PlayParty> responseMessage = new ResponseMessage<>(result);
        if(result == null)
        {
            responseMessage.setResponseCode(ResponseCode.NOT_FOUND);
            responseMessage.setMessage("PlayParty not found");
        }
        else
        {
            responseMessage.setResponseCode(ResponseCode.SUCCESS);
        }
        return responseMessage;
    }

    public ResponseMessage<PlayParty> getPlayParty(Integer id) {
        var result =  storeOrderRepository.findById(id);
        ResponseMessage<PlayParty> responseMessage = new ResponseMessage<>(result.get());
        if(!result.isPresent())
        {
            responseMessage.setResponseCode(ResponseCode.NOT_FOUND);
            responseMessage.setMessage("PlayParty not found");
        }
        else
        {
            responseMessage.setResponseCode(ResponseCode.SUCCESS);
        }
        return responseMessage;
    }

    public ResponseMessage<PlayParty> getPlayPartyByCode(String code) {
        var result =  storeOrderRepository.findByCode(code);
        ResponseMessage<PlayParty> responseMessage = new ResponseMessage<>(result.get());
        if(!result.isPresent())
        {
            responseMessage.setResponseCode(ResponseCode.NOT_FOUND);
            responseMessage.setMessage("PlayParty not found");
        }
        else
        {
            responseMessage.setResponseCode(ResponseCode.SUCCESS);
        }
        return responseMessage;
    }

    public ResponseMessage<PlayParty> updatePlayPartyPlayer(int id, int playerId) {
        var result =  storeOrderRepository.findById(id);

        ResponseMessage<PlayParty> responseMessage = new ResponseMessage<>(result.get());
        if(!result.isPresent())
        {
            responseMessage.setResponseCode(ResponseCode.NOT_FOUND);
            responseMessage.setMessage("PlayParty not found");
        }
        else
        {
            if(result.get().getUserIdB()==0)
            {
                result.get().setUserIdB(playerId);
                var data = storeOrderRepository.save(result.get());
                responseMessage.setResponse(data);
                responseMessage.setResponseCode(ResponseCode.SUCCESS);
            }
            else {
                responseMessage.setResponseCode(ResponseCode.ERROR);
                responseMessage.setMessage("A second player already in this party, you are a spectator");
            }
        }
        return responseMessage;
    }


    public ResponseMessage<PlayParty> playerAttack(int id) {
        var result =  storeOrderRepository.findById(id);

        ResponseMessage<PlayParty> responseMessage = new ResponseMessage<>(result.get());
        if(!result.isPresent())
        {
            responseMessage.setResponseCode(ResponseCode.NOT_FOUND);
            responseMessage.setMessage("PlayParty not found");
            return responseMessage;
        }
        else
        {
            if(result.get().getWinnerId()!=0)
            {
                if(result.get().getUserIdA()==result.get().getWinnerId())
                {
                    responseMessage.setResponseCode(ResponseCode.SUCCESS);
                    responseMessage.setMessage("Player A is the winner !");
                    return responseMessage;
                }
                else if(result.get().getUserIdB()==result.get().getWinnerId())
                {
                    responseMessage.setResponseCode(ResponseCode.SUCCESS);
                    responseMessage.setMessage("Player B is the winner !");
                    return responseMessage;
                }
                else
                {
                    responseMessage.setResponseCode(ResponseCode.ERROR);
                    responseMessage.setMessage("There is an error, neither of the two player is the winner, the game is corrupted !");
                    return responseMessage;
                }

            }
            if(result.get().getUserIdB()==0)
            {
                responseMessage.setResponseCode(ResponseCode.NOT_FOUND);
                responseMessage.setMessage("Second PlayParty not found");
                return responseMessage;
            }
            else if(result.get().getCardPlayerA()==0 || result.get().getCardPlayerB()==0)
            {
                responseMessage.setResponseCode(ResponseCode.NOT_FOUND);
                responseMessage.setMessage("All player need to select a card to play");
                return responseMessage;
            }
            else {
                var first_player = userRepository.findById(result.get().getUserIdA());
                var second_player = userRepository.findById(result.get().getUserIdB());

                var first_card = cardRepository.findById(result.get().getCardPlayerA());
                var second_card = cardRepository.findById(result.get().getCardPlayerB());

                if(!first_player.isPresent() || !second_player.isPresent())
                {
                    responseMessage.setResponseCode(ResponseCode.NOT_FOUND);
                    responseMessage.setMessage("One of the two player dont exist");
                    return responseMessage;
                }
                else if(!first_card.isPresent() || !second_card.isPresent())
                {
                    responseMessage.setResponseCode(ResponseCode.NOT_FOUND);
                    responseMessage.setMessage("One of the two card dont exist");
                    return responseMessage;
                }
                else
                {
                    var player_a = first_player.get();
                    var player_b = second_player.get();

                    var card_a = first_card.get();
                    var card_b = second_card.get();




                    if(result.get().getCurrentPlayerId()==player_a.getId())
                    {
                        if(card_b.getHp()<card_a.getAttack())
                        {
                            card_b.setHp(0.0);
                            result.get().setWinnerId(player_a.getId());
                            player_a.setAccount(player_a.getAccount()+40.0);
                        }
                        else
                        {
                            card_b.setHp(card_b.getHp()-card_a.getAttack());
                        }
                    }
                    else
                    {
                        if(card_a.getHp()<card_b.getAttack())
                        {
                            card_a.setHp(0.0);
                            result.get().setWinnerId(player_b.getId());
                            player_b.setAccount(player_b.getAccount()+40.0);
                        }
                        else
                        {
                            card_a.setHp(card_a.getHp()-card_b.getAttack());
                        }
                    }

                    if(result.get().getCurrentPlayerId()==0)
                    {
                        result.get().setCurrentPlayerId(player_a.getId());
                    }
                    else if(result.get().getCurrentPlayerId()!=0)
                    {
                        if(result.get().getCurrentPlayerId()==player_a.getId())
                        {
                            result.get().setCurrentPlayerId(player_b.getId());
                        }
                        else
                        {
                            result.get().setCurrentPlayerId(player_a.getId());
                        }
                    }

                    userRepository.save(player_a);
                    userRepository.save(player_b);

                    cardRepository.save(card_a);
                    cardRepository.save(card_b);

                    storeOrderRepository.save(result.get());
                }
            }
        }
        return responseMessage;
    }

    public ResponseMessage<List<PlayParty>> getAllPlayParty(Optional<Pageable> filter) {

        ResponseMessage<List<PlayParty>> responseMessage = null;
        try {
            if(filter.isPresent())
            {
                var data = storeOrderRepository.findAll(filter.get());
                responseMessage = new ResponseMessage<>(data.getContent());
                responseMessage.setResponseCode(ResponseCode.SUCCESS);
            }
            else
            {
                var data = storeOrderRepository.findAll();
                responseMessage = new ResponseMessage<>(data);
                responseMessage.setResponseCode(ResponseCode.SUCCESS);
            }
        }
        catch (Exception e)
        {
            responseMessage = new ResponseMessage<>(new ArrayList<>());
            responseMessage.setResponseCode(ResponseCode.ERROR);
            responseMessage.setMessage("Failed to get all PlayParty " +(filter.isPresent()?"(with filter)":"") + " with error :"+e.getMessage());
        }
        return responseMessage;
    }



    public ResponseMessage<PlayParty> deletePlayParty(PlayParty storeOrder) {
        storeOrderRepository.delete(storeOrder);
        var result = storeOrderRepository.findById(storeOrder.getId());

        if(!result.isPresent())
        {
            ResponseMessage<PlayParty> responseMessage = new ResponseMessage<>(storeOrder);
            responseMessage.setResponseCode(ResponseCode.SUCCESS);
            return responseMessage;
        }
        else
        {
            ResponseMessage<PlayParty> responseMessage = new ResponseMessage<>(storeOrder);
            responseMessage.setMessage("PlayParty deletion failed");
            responseMessage.setResponseCode(ResponseCode.ERROR);
            return responseMessage;
        }

    }

    public ResponseMessage<PlayParty> deletePlayParty(int storeOrder) {
        var result = storeOrderRepository.findById(storeOrder);
        if(result.isPresent())
        {
            return deletePlayParty(result.get());
        }
        else
        {
            ResponseMessage<PlayParty> responseMessage = new ResponseMessage<>(null);
            responseMessage.setMessage("PlayParty deletion failed");
            responseMessage.setResponseCode(ResponseCode.ERROR);
            return responseMessage;
        }
    }
}