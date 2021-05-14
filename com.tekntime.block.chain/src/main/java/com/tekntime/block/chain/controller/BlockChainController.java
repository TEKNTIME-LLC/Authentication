package com.tekntime.block.chain.controller;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tekntime.block.chain.model.Block;
import com.tekntime.block.chain.service.BlockChainService;

@RestController
@RequestMapping("/blockchain")
public class BlockChainController {
	
	Logger logger = LoggerFactory.getLogger(BlockChainController.class);


	@Autowired
	private BlockChainService service; 
		
	@RequestMapping( path="/register/user", method=RequestMethod.POST)
	public List<Block> create( @RequestBody String user, @RequestBody String hashedPwd, @RequestBody String publickey ) throws Exception {
		List<Block> result = service.register(user, hashedPwd, publickey);
	    return result;
	}
	@RequestMapping(path="/update/block", method=RequestMethod.PUT)
	public List<Block> update(@RequestBody String user, @RequestBody String txn) throws Exception {
		
		List<Block> result =  service.update(user, txn);
	     return result;
	}	
	
	@RequestMapping(path="/validate/user", method=RequestMethod.POST)
	public  Boolean reset(@RequestParam List<Block> block) {
	     return service.validate(block);
	}

}
