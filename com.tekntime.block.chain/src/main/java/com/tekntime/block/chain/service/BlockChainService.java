package com.tekntime.block.chain.service;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import com.tekntime.block.chain.model.Block;
import com.tekntime.block.chain.util.StringUtil;

@Service
public class BlockChainService {
	
	Logger logger = LoggerFactory.getLogger(BlockChainService.class);
	private final static int difficulty = 5;

	public  String createMock() {	
		List<Block> blockchain = new ArrayList<Block>();
		
		logger.info("Trying to Mine block 1... ");
		addBlock(new Block("Hi im the first block", "0"), blockchain);
		
		logger.info("Trying to Mine block 2... ");
		addBlock(new Block("Yo im the second block",blockchain.get(blockchain.size()-1).getHash()), blockchain);
		
		logger.info("Trying to Mine block 3... ");
		addBlock(new Block("Hey im the third block",blockchain.get(blockchain.size()-1).getHash()), blockchain);	
		
		logger.info("\nBlockchain is Valid: " + isChainValid(blockchain));
		
		String blockchainJson = StringUtil.getJson(blockchain);
		logger.info(blockchainJson);
		return blockchainJson;
	}
	

	
	public  List<Block> get(String user) {	
		List<Block> blockchain = new ArrayList<Block>();
		//get user block from somewhere
		
		return blockchain;
	}
	
	public  List<Block> register(String user, String hashedPwd, String publickey) {	
		List<Block> blockchain = new ArrayList<Block>();
		addBlock(new Block(user, "0"), blockchain);
		addBlock(new Block(hashedPwd,blockchain.get(blockchain.size()-1).getHash()), blockchain);
		addBlock(new Block(publickey,blockchain.get(blockchain.size()-1).getHash()), blockchain);	
		return blockchain;
	}
	
	public  List<Block> update(String user, String blockTxn) {	
		List<Block> blockchain=get(user);
		addBlock(new Block(blockTxn,blockchain.get(blockchain.size()-1).getHash()), blockchain);	
		return blockchain;
	}
	
	public  List<Block> update(String blockTxn, List<Block> blockchain) {	
		addBlock(new Block(blockTxn,blockchain.get(blockchain.size()-1).getHash()), blockchain);	
		return blockchain;
	}
	

	
	public  Boolean validate(List<Block> blockchain) {	
		return isChainValid(blockchain);
	}
	
	private  Boolean isChainValid(List<Block> blockchain) {
		Block currentBlock; 
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		//loop through blockchain to check hashes:
		for(int i=1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.getHash().equals(currentBlock.calculateHash()) ){
				logger.info("Current Hashes not equal");			
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.getHash().equals(currentBlock.getPreviousHash()) ) {
				logger.info("Previous Hashes not equal");
				return false;
			}
			//check if hash is solved
			if(!currentBlock.getHash().substring( 0, difficulty).equals(hashTarget)) {
				logger.info("This block hasn't been mined");
				return false;
			}
			
		}
		return true;
	}
	
	public  void addBlock(Block newBlock, List<Block> blockchain) {
		newBlock.mineBlock(difficulty);
		blockchain.add(newBlock);
	}
}
