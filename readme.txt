/auth/login --- Todos-- post
/auth/register --- admin-- post
/auth/{username} --- admin -- Delete y patch
/auth   --- admin -- get

/api/client -- admin, cajero -- get
/api/client -- admin, cajero -- post
/api/client/{id} -- admin, cajero -- Get
/api/client/{id} -- admin -- delete
/api/client/{id} -- admin -- put
/api/client/dni/{dni} -- admin, cajero -- Get
/api/client/email/{email} -- admin, cajero -- Get
/api/client/phone/{phone} -- admin, cajero -- Get

/api/iva -- admin, cajero -- Get

/api/category -- admin, cajero -- Get
/api/category -- admin -- post
/api/category/{id} -- admin -- delete
/api/category/{id} -- admin -- put
/api/category/{id} -- admin, cajero -- Get
/api/category/name/{name} -- admin, cajero -- Get

/api/productos -- admin, cajero -- Get
/api/productos -- admin -- post
/api/productos/{id} -- admin, cajero -- get
/api/productos/{id} -- admin -- put
/api/productos/activation/{id} -- admin -- put //activa o desactiva el producto forzozamente.
/api/productos/deactivate -- admin, cajero -- get //muestra productos fuera de servicios
/api/productos/iva -- admin -- put // cambia el iva de todos los productos existentes.
/api/productos/name/{name} -- admin, cajero -- get

/api/sales -- admin, cajero -- Get
/api/sales -- admin, cajero -- post
/api/sales/{id} -- admin, cajero -- Get
/api/sales/client/{clientId} -- admin, cajero -- Get
/api/sales/date-range -- admin, cajero -- Get
/api/sales/date/{date} -- admin, cajero -- Get
/api/sales/update-client -- admin, cajero -- put
