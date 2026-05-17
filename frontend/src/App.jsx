import { useState, useEffect } from 'react';
import axios from 'axios';

function App() {
  const [activeTab, setActiveTab] = useState('clientes');

  const [clients, setClients] = useState([]);
  const [name, setName] = useState('');
  const [lastname, setLastname] = useState('');
  const [docnumber, setDocnumber] = useState('');

  const [products, setProducts] = useState([]);
  const [prodDescription, setProdDescription] = useState('');
  const [prodCode, setProdCode] = useState('');
  const [prodPrice, setProdPrice] = useState('');
  const [prodStock, setProdStock] = useState('');

  const [invoices, setInvoices] = useState([]);
  const [selectedClientId, setSelectedClientId] = useState('');
  const [selectedProductId, setSelectedProductId] = useState('');
  const [amount, setAmount] = useState(1);
  const [cart, setCart] = useState([]);

  const CLIENT_API = 'http://localhost:8080/api/client/';
  const PRODUCT_API = 'http://localhost:8080/api/product/';
  const INVOICE_API = 'http://localhost:8080/api/invoice/';

  const fetchClients = async () => {
    try {
      const response = await axios.get(CLIENT_API);
      setClients(response.data);
    } catch (error) {
      console.error("Error al traer los clientes:", error);
    }
  };

  const fetchProducts = async () => {
    try {
      const response = await axios.get(PRODUCT_API);
      setProducts(response.data);
    } catch (error) {
      console.error("Error al traer los productos:", error);
    }
  };

  const fetchInvoices = async () => {
    try {
      const response = await axios.get(INVOICE_API);
      setInvoices(response.data);
    } catch (error) {
      console.error("Error al traer las facturas:", error);
    }
  };

  useEffect(() => {
    fetchClients();
    fetchProducts();
    fetchInvoices();
  }, []);

  const handleClientSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post(CLIENT_API, { name, lastname, docnumber });
      setName(''); setLastname(''); setDocnumber('');
      fetchClients();
    } catch (error) {
      alert("Error: " + (error.response?.data || error.message));
    }
  };

  const handleProductSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post(PRODUCT_API, {
        description: prodDescription,
        code: prodCode,
        price: parseFloat(prodPrice),
        stock: parseInt(prodStock)
      });
      setProdDescription(''); setProdCode(''); setProdPrice(''); setProdStock('');
      fetchProducts();
    } catch (error) {
      alert("Error al guardar producto: " + (error.response?.data || error.message));
    }
  };

  const addToCart = (e) => {
    e.preventDefault();
    if (!selectedProductId) return;
    
    const product = products.find(p => p.id === parseInt(selectedProductId));
    if (!product) return;

    if (amount > product.stock) {
      alert(`Stock insuficiente. Solo quedan ${product.stock} unidades.`);
      return;
    }

    const existingItem = cart.find(item => item.product.id === product.id);
    if (existingItem) {
      const newAmount = existingItem.amount + parseInt(amount);
      if (newAmount > product.stock) {
        alert(`No podés agregar más. El stock total es de ${product.stock} unidades.`);
        return;
      }
      setCart(cart.map(item => 
        item.product.id === product.id ? { ...item, amount: newAmount } : item
      ));
    } else {
      setCart([...cart, { product, amount: parseInt(amount) }]);
    }

    setSelectedProductId('');
    setAmount(1);
  };

  const removeFromCart = (productId) => {
    setCart(cart.filter(item => item.product.id !== productId));
  };

  const calculateTotal = () => {
    return cart.reduce((acc, item) => acc + (item.product.price * item.amount), 0);
  };

  const handleInvoiceSubmit = async (e) => {
    e.preventDefault();
    if (!selectedClientId) {
      alert("Por favor, seleccioná un cliente.");
      return;
    }
    if (cart.length === 0) {
      alert("El carrito debe tener al menos un producto.");
      return;
    }

    const clientObj = clients.find(c => c.id === parseInt(selectedClientId));

    const invoicePayload = {
      total: calculateTotal(),
      client: clientObj,
      invoiceDetailsList: cart.map(item => ({
        product: item.product,
        amount: item.amount,
        price: item.product.price
      }))
    };

    try {
      await axios.post(INVOICE_API, invoicePayload);
      alert("Factura generada con éxito.");
      setCart([]);
      setSelectedClientId('');
      fetchInvoices();
      fetchProducts();
    } catch (error) {
      alert("Error al guardar la factura: " + (error.response?.data || error.message));
    }
  };

  return (
    <div className="flex h-screen bg-gray-100 font-sans text-gray-800">
      
      <aside className="w-64 bg-slate-900 text-white flex flex-col shadow-xl">
        <div className="p-5 text-xl font-bold tracking-wider border-b border-slate-800 flex items-center gap-2">
          <span>📦</span> InventoryApp
        </div>
        <nav className="flex-1 p-4 space-y-2">
          <button 
            onClick={() => setActiveTab('clientes')}
            className={`w-full flex items-center gap-3 px-4 py-3 rounded-lg font-medium transition-colors ${
              activeTab === 'clientes' ? 'bg-indigo-600 text-white' : 'text-slate-400 hover:bg-slate-800 hover:text-white'
            }`}
          >
            👥 Clientes
          </button>
          <button 
            onClick={() => setActiveTab('productos')}
            className={`w-full flex items-center gap-3 px-4 py-3 rounded-lg font-medium transition-colors ${
              activeTab === 'productos' ? 'bg-indigo-600 text-white' : 'text-slate-400 hover:bg-slate-800 hover:text-white'
            }`}
          >
            🏷️ Productos
          </button>
          <button 
            onClick={() => setActiveTab('facturas')}
            className={`w-full flex items-center gap-3 px-4 py-3 rounded-lg font-medium transition-colors ${
              activeTab === 'facturas' ? 'bg-indigo-600 text-white' : 'text-slate-400 hover:bg-slate-800 hover:text-white'
            }`}
          >
            📄 Facturas
          </button>
        </nav>
        <div className="p-4 border-t border-slate-800 text-xs text-slate-500 text-center">
          v1.0.0 — MoyanoManchón
        </div>
      </aside>

      <main className="flex-1 flex flex-col overflow-y-auto">
        <header className="bg-white shadow-sm px-8 py-4 flex justify-between items-center border-b border-gray-200">
          <h1 className="text-2xl font-bold text-gray-900 capitalize">Gestión de {activeTab}</h1>
          <div className="flex items-center gap-2 text-sm text-gray-500 bg-gray-50 px-3 py-1.5 rounded-full border border-gray-200">
            <span className="w-2 h-2 bg-emerald-500 rounded-full animate-pulse"></span>
            Backend Conectado
          </div>
        </header>

        <div className="p-8 max-w-7xl w-full mx-auto space-y-8">
          
          {activeTab === 'clientes' && (
            <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
              <div className="bg-white p-6 rounded-xl shadow-md border border-gray-200 h-fit">
                <h2 className="text-lg font-semibold text-gray-900 mb-4">Añadir Nuevo Cliente</h2>
                <form onSubmit={handleClientSubmit} className="space-y-4">
                  <div>
                    <label className="block text-xs font-semibold uppercase tracking-wider text-gray-500 mb-1">Nombre</label>
                    <input type="text" placeholder="Ej. Juan" value={name} onChange={(e) => setName(e.target.value)} required className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500" />
                  </div>
                  <div>
                    <label className="block text-xs font-semibold uppercase tracking-wider text-gray-500 mb-1">Apellido</label>
                    <input type="text" placeholder="Ej. Pérez" value={lastname} onChange={(e) => setLastname(e.target.value)} required className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500" />
                  </div>
                  <div>
                    <label className="block text-xs font-semibold uppercase tracking-wider text-gray-500 mb-1">DNI</label>
                    <input type="text" placeholder="Ej. 12345678" value={docnumber} onChange={(e) => setDocnumber(e.target.value)} required className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500" />
                  </div>
                  <button type="submit" className="w-full bg-indigo-600 hover:bg-indigo-700 text-white font-medium py-2 px-4 rounded-lg shadow transition-colors">Guardar Cliente</button>
                </form>
              </div>

              <div className="lg:col-span-2 bg-white rounded-xl shadow-md border border-gray-200 overflow-hidden">
                <div className="px-6 py-4 border-b border-gray-200 bg-gray-50"><h2 className="font-semibold text-gray-900">Listado Registrado</h2></div>
                <table className="w-full text-left border-collapse">
                  <thead>
                    <tr className="bg-gray-100 text-gray-600 text-xs uppercase tracking-wider font-semibold border-b border-gray-200">
                      <th className="py-3 px-6 w-16">ID</th>
                      <th className="py-3 px-6">Nombre</th>
                      <th className="py-3 px-6">Apellido</th>
                      <th className="py-3 px-6">DNI</th>
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-gray-200 text-sm">
                    {clients.map((client) => (
                      <tr key={client.id} className="hover:bg-gray-50 transition-colors">
                        <td className="py-4 px-6 font-mono text-gray-400">{client.id}</td>
                        <td className="py-4 px-6 font-medium text-gray-900">{client.name}</td>
                        <td className="py-4 px-6 text-gray-600">{client.lastname}</td>
                        <td className="py-4 px-6 text-gray-600 font-mono">{client.docnumber}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          )}

          {activeTab === 'productos' && (
            <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
              <div className="bg-white p-6 rounded-xl shadow-md border border-gray-200 h-fit">
                <h2 className="text-lg font-semibold text-gray-900 mb-4">Añadir Nuevo Producto</h2>
                <form onSubmit={handleProductSubmit} className="space-y-4">
                  <div>
                    <label className="block text-xs font-semibold uppercase tracking-wider text-gray-500 mb-1">Código del Producto</label>
                    <input type="text" placeholder="Ej. MON-24-GAMER" value={prodCode} onChange={(e) => setProdCode(e.target.value)} required className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500" />
                  </div>
                  <div>
                    <label className="block text-xs font-semibold uppercase tracking-wider text-gray-500 mb-1">Descripción / Nombre</label>
                    <input type="text" placeholder="Ej. Monitor Gamer 24\" value={prodDescription} onChange={(e) => setProdDescription(e.target.value)} required className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500" />
                  </div>
                  <div className="grid grid-cols-2 gap-4">
                    <div>
                      <label className="block text-xs font-semibold uppercase tracking-wider text-gray-500 mb-1">Precio ($)</label>
                      <input type="number" step="0.01" placeholder="Ej. 1500.50" value={prodPrice} onChange={(e) => setProdPrice(e.target.value)} required className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500" />
                    </div>
                    <div>
                      <label className="block text-xs font-semibold uppercase tracking-wider text-gray-500 mb-1">Stock Inicial</label>
                      <input type="number" placeholder="Ej. 25" value={prodStock} onChange={(e) => setProdStock(e.target.value)} required className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500" />
                    </div>
                  </div>
                  <button type="submit" className="w-full bg-indigo-600 hover:bg-indigo-700 text-white font-medium py-2 px-4 rounded-lg shadow transition-colors">Guardar Producto</button>
                </form>
              </div>

              <div className="lg:col-span-2 bg-white rounded-xl shadow-md border border-gray-200 overflow-hidden">
                <div className="px-6 py-4 border-b border-gray-200 bg-gray-50"><h2 className="font-semibold text-gray-900">Inventario de Productos</h2></div>
                <table className="w-full text-left border-collapse">
                  <thead>
                    <tr className="bg-gray-100 text-gray-600 text-xs uppercase tracking-wider font-semibold border-b border-gray-200">
                      <th className="py-3 px-6 w-16">ID</th>
                      <th className="py-3 px-6">Código</th>
                      <th className="py-3 px-6">Descripción</th>
                      <th className="py-3 px-6">Precio</th>
                      <th className="py-3 px-6">Stock</th>
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-gray-200 text-sm">
                    {products.map((product) => (
                      <tr key={product.id} className="hover:bg-gray-50 transition-colors">
                        <td className="py-4 px-6 font-mono text-gray-400">{product.id}</td>
                        <td className="py-4 px-6 font-mono font-semibold text-xs text-indigo-600">{product.code}</td>
                        <td className="py-4 px-6 font-medium text-gray-900">{product.description}</td>
                        <td className="py-4 px-6 text-gray-600">${product.price}</td>
                        <td className="py-4 px-6">
                          <span className={`px-2.5 py-0.5 rounded-full text-xs font-medium ${product.stock > 5 ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'}`}>
                            {product.stock} unidades
                          </span>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          )}

          {activeTab === 'facturas' && (
            <div className="space-y-8">
              <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                
                <div className="bg-white p-6 rounded-xl shadow-md border border-gray-200 h-fit space-y-6">
                  <h2 className="text-lg font-semibold text-gray-900">Nueva Factura</h2>
                  
                  <div>
                    <label className="block text-xs font-semibold uppercase tracking-wider text-gray-500 mb-1">Seleccionar Cliente</label>
                    <select 
                      value={selectedClientId} 
                      onChange={(e) => setSelectedClientId(e.target.value)}
                      className="w-full px-3 py-2 border border-gray-300 rounded-lg bg-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
                    >
                      <option value="">-- Elegir Cliente --</option>
                      {clients.map(c => (
                        <option key={c.id} value={c.id}>{c.lastname}, {c.name} (DNI: {c.docnumber})</option>
                      ))}
                    </select>
                  </div>

                  <hr className="border-gray-200" />

                  <form onSubmit={addToCart} className="space-y-4">
                    <div>
                      <label className="block text-xs font-semibold uppercase tracking-wider text-gray-500 mb-1">Agregar Producto</label>
                      <select 
                        value={selectedProductId} 
                        onChange={(e) => setSelectedProductId(e.target.value)}
                        required
                        className="w-full px-3 py-2 border border-gray-300 rounded-lg bg-white focus:outline-none focus:ring-2 focus:ring-indigo-500"
                      >
                        <option value="">-- Elegir Producto --</option>
                        {products.map(p => (
                          <option key={p.id} value={p.id} disabled={p.stock <= 0}>
                            {p.description} - ${p.price} ({p.stock} disp.)
                          </option>
                        ))}
                      </select>
                    </div>

                    <div>
                      <label className="block text-xs font-semibold uppercase tracking-wider text-gray-500 mb-1">Cantidad</label>
                      <input 
                        type="number" 
                        min="1" 
                        value={amount} 
                        onChange={(e) => setAmount(e.target.value)}
                        required 
                        className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500" 
                      />
                    </div>

                    <button type="submit" className="w-full bg-slate-800 hover:bg-slate-900 text-white font-medium py-2 px-4 rounded-lg transition-colors">
                      Añadir al Detalle
                    </button>
                  </form>
                </div>

                <div className="lg:col-span-2 bg-white rounded-xl shadow-md border border-gray-200 overflow-hidden flex flex-col justify-between">
                  <div>
                    <div className="px-6 py-4 border-b border-gray-200 bg-gray-50 flex justify-between items-center">
                      <h2 className="font-semibold text-gray-900">Detalle de la Venta actual</h2>
                      <span className="text-xs bg-indigo-50 text-indigo-700 font-semibold px-2.5 py-1 rounded-md">
                        {cart.length} ítems seleccionados
                      </span>
                    </div>
                    <table className="w-full text-left border-collapse">
                      <thead>
                        <tr className="bg-gray-100 text-gray-600 text-xs uppercase tracking-wider font-semibold border-b border-gray-200">
                          <th className="py-3 px-6">Código</th>
                          <th className="py-3 px-6">Descripción</th>
                          <th className="py-3 px-6">Precio Unit.</th>
                          <th className="py-3 px-6">Cant.</th>
                          <th className="py-3 px-6">Subtotal</th>
                          <th className="py-3 px-6 w-16"></th>
                        </tr>
                      </thead>
                      <tbody className="divide-y divide-gray-200 text-sm">
                        {cart.map((item) => (
                          <tr key={item.product.id} className="hover:bg-gray-50 transition-colors">
                            <td className="py-3 px-6 font-mono text-xs text-indigo-600 font-semibold">{item.product.code}</td>
                            <td className="py-3 px-6 font-medium text-gray-900">{item.product.description}</td>
                            <td className="py-3 px-6 text-gray-600">${item.product.price}</td>
                            <td className="py-3 px-6 font-mono font-medium">{item.amount}</td>
                            <td className="py-3 px-6 text-gray-900 font-semibold">${(item.product.price * item.amount).toFixed(2)}</td>
                            <td className="py-3 px-6 text-right">
                              <button 
                                onClick={() => removeFromCart(item.product.id)}
                                className="text-red-500 hover:text-red-700 font-medium transition-colors"
                              >
                                Quitar
                              </button>
                            </td>
                          </tr>
                        ))}
                        {cart.length === 0 && (
                          <tr>
                            <td colSpan="6" className="py-12 text-center text-gray-400 font-medium">El detalle está vacío. Seleccioná productos a la izquierda.</td>
                          </tr>
                        )}
                      </tbody>
                    </table>
                  </div>

                  <div className="p-6 bg-gray-50 border-t border-gray-200 flex flex-col sm:flex-row justify-between items-center gap-4">
                    <div className="text-center sm:text-left">
                      <span className="text-xs uppercase tracking-wider font-bold text-gray-400 block">Total Facturado</span>
                      <span className="text-3xl font-black text-slate-900">${calculateTotal().toFixed(2)}</span>
                    </div>
                    <button 
                      onClick={handleInvoiceSubmit}
                      className="w-full sm:w-auto bg-indigo-600 hover:bg-indigo-700 text-white font-semibold py-3 px-8 rounded-lg shadow-md transition-colors"
                    >
                      Emitir y Registrar Factura
                    </button>
                  </div>
                </div>

              </div>

              <div className="bg-white rounded-xl shadow-md border border-gray-200 overflow-hidden">
                <div className="px-6 py-4 border-b border-gray-200 bg-gray-50">
                  <h2 className="font-semibold text-gray-900">Historial de Facturas Emitidas</h2>
                </div>
                <table className="w-full text-left border-collapse">
                  <thead>
                    <tr className="bg-gray-100 text-gray-600 text-xs uppercase tracking-wider font-semibold border-b border-gray-200">
                      <th className="py-3 px-6 w-16">Nro</th>
                      <th className="py-3 px-6">Cliente</th>
                      <th className="py-3 px-6">Fecha Emisión</th>
                      <th className="py-3 px-6">Total General</th>
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-gray-200 text-sm">
                    {invoices.map((inv) => (
                      <tr key={inv.id} className="hover:bg-gray-50 transition-colors">
                        <td className="py-4 px-6 font-mono text-indigo-600 font-bold"># {inv.id}</td>
                        <td className="py-4 px-6 font-medium text-gray-900">
                          {inv.client ? `${inv.client.lastname}, ${inv.client.name}` : 'Cliente No Disponible'}
                        </td>
                        <td className="py-4 px-6 text-gray-500">
                          {inv.createdAt ? new Date(inv.createdAt).toLocaleString() : 'Recién creada'}
                        </td>
                        <td className="py-4 px-6 text-gray-900 font-bold text-base">${inv.total?.toFixed(2)}</td>
                      </tr>
                    ))}
                    {invoices.length === 0 && (
                      <tr>
                        <td colSpan="4" className="py-8 text-center text-gray-400 font-medium">No se registran facturas emitidas en el sistema todavía.</td>
                      </tr>
                    )}
                  </tbody>
                </table>
              </div>
            </div>
          )}

        </div>
      </main>
    </div>
  );
}

export default App;
