local function set_lock(key, value, expired)
	local lock_key = tostring(key)..".lock"
	local lock_val = tostring(value)
	local lock_expired = tonumber(expired)
	local ret_exists = redis.call("exists", lock_key)
	if(1 == ret_exists)
		return 404
	end
	
	local ret_set = redis.call("set", lock_key, lock_val, "NX");
	if(ret_set == nil)
		return 405
	end
	
	local ret_expired = redis.call("expire", lock_key, lock_expired)
	if(ret_expired != 1)
		return 406
	end
	
	return 200
end

return set_lock(KEYS[1], KEYS[2], KEYS[3])