/**
 * 
 */
// keep small; mostly used by employees/employee-form templates
async function fetchJson(url, opts) {
  const res = await fetch(url, opts);
  if (!res.ok) throw new Error('HTTP ' + res.status);
  return await res.json();
}
